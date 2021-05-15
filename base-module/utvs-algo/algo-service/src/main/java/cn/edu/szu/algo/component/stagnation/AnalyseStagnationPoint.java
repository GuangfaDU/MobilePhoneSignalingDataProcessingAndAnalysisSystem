package cn.edu.szu.algo.component.stagnation;

import cn.edu.szu.algo.dao.SignalDataDao;
import cn.edu.szu.algo.entity.ClusteringResultMap;
import cn.edu.szu.algo.entity.Point;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.entity.StagnationResultMap;
import cn.edu.szu.algo.interfaces.stagnation.IAnalyseStagnationPoint;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class AnalyseStagnationPoint implements IAnalyseStagnationPoint {

    private final SignalDataDao signalDataDao;
    private final MongoTemplate mongoTemplate;

    private static final Pattern PATTERN = Pattern.compile("\\d+[.]?\\d+");

    @Autowired
    public AnalyseStagnationPoint(SignalDataDao signalDataDao, MongoTemplate mongoTemplate) {
        this.signalDataDao = signalDataDao;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void analyzeStagnationPoint() {
        generateStagnationFiles();
        analyze();
    }

    private void generateStagnationFiles() {
        QueryWrapper<SignalData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("imsi").groupBy("imsi");
        List<SignalData> signalDataList = signalDataDao.selectList(queryWrapper);
        signalDataList.forEach(signalData -> {
            String imsi = signalData.getImsi();

            Query query = new Query();
            query.addCriteria(Criteria.where("imsi").is(imsi));
            List<StagnationResultMap> resultMaps = mongoTemplate.find(query, StagnationResultMap.class);
            File file = new File(DEFAULT_WORD_DIR + imsi + ".csv");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("lat,lng,start,time");
                writer.newLine();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                resultMaps.forEach(item -> {
                    try {
                        Date startTime = simpleDateFormat.parse(item.getStartTime());
                        Date endTime = simpleDateFormat.parse(item.getEndTime());
                        long time = endTime.getTime() - startTime.getTime();

                        writer.write(item.getStagnationPoint().getLatitude() + ","
                                + item.getStagnationPoint().getLongitude() + ","
                                + parseInt(item.getStartTime()) + ","
                                + ((double)time / 1000 / 60 / 60));
                        writer.newLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static Integer parseInt(String time) {
        String[] split = time.split(" ")[1].split(":");
        return Integer.parseInt(split[0]);
    }

    private void analyze() {
        File file = new File(DEFAULT_WORD_DIR);
        File[] files = file.listFiles();
        assert files != null;
        for (File item : files) {
            usePythonModule(item);
            item.delete();
        }
    }

    private void usePythonModule(File file) {
        Process process;
        String[] commands = new String[]{"python", DEFAULT_PYTHON_MODULE_DIR, ""};
        commands[2] = file.getAbsolutePath();

        try {
            process = Runtime.getRuntime().exec(commands);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> res = new ArrayList<>(4);
            while ((line = reader.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                while (matcher.find()) {
                    res.add(matcher.group());
                }
            }

            List<Point> poi = new LinkedList<>();
            Point point = null;
            for (int i = 0; i < res.size(); ++i) {
                if (i % 2 == 0) {
                    point = new Point();
                    point.setLatitude(Double.valueOf(res.get(i)));
                    continue;
                }

                point.setLongitude(Double.valueOf(res.get(i)));
                poi.add(point);
            }

            if (!poi.isEmpty()) {
                save(file.getName().split("\\.")[0], poi);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(String imsi, List<Point> poi) {
        Query query = new Query();
        query.addCriteria(Criteria.where("imsi").is(imsi));
        Update update = new Update();
        update.set("poi", poi);
        mongoTemplate.upsert(query, update, ClusteringResultMap.class);
    }
}
