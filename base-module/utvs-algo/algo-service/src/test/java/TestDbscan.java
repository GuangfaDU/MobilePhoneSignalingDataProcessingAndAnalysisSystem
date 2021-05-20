import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.stagnation.AnalyseStagnationPoint;
import cn.edu.szu.algo.dao.SignalDataDao;
import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.entity.StagnationResultMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgoServiceApplication.class)
public class TestDbscan {

    @Autowired
    private SignalDataDao signalDataDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AnalyseStagnationPoint analyseStagnationPoint;

    @Test
    public void testDbscan() {
        File file = new File("D:/GuangfaDU/data/testDbscan.csv");
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("lat,lng,start,time");
            writer.newLine();

//            QueryWrapper<SignalData> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("imsi", "1DB13CCF689CD637DA6C0886A71119F5");
//            List<SignalData> signalDataList = signalDataDao.selectList(queryWrapper);
//            signalDataList.forEach(item -> {
//                try {
//                    writer.write(item.getLatitude() + ","
//                            + item.getLongitude() + ","
//                            + parseInt(item.getStart()) + ","
//                            + parseInt(item.getEnd()));
//                    writer.newLine();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
            Query query = new Query();
            query.addCriteria(Criteria.where("imsi").is("CB07135773D7357D6B4CB27530CD0D07"));
            List<StagnationResultMap> resultMaps = mongoTemplate.find(query, StagnationResultMap.class);
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
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    private static Integer parseInt(String time) {
        String[] split = time.split(" ")[1].split(":");
        return Integer.parseInt(split[0]);
    }

    @Test
    public void testUsePython() {
        Process process;
        String[] commands = new String[]{"python",
                "D:/GuangfaDU/code/python_module/main.py",
                "D:/GuangfaDU/data/testDbscan.csv"};
        try {
            process = Runtime.getRuntime().exec(commands);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Pattern pattern = Pattern.compile("\\d+[.]?\\d+");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    System.out.println(matcher.group());
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFromMongo() {
        Query query = new Query();
        query.addCriteria(Criteria.where("imsi").is("168385E9E4BC6A749D069EB39DDAAD12"));
        List<AggregationResultMap> resultMaps = mongoTemplate.find(query, AggregationResultMap.class);
        resultMaps.forEach(System.out::println);
    }

    @Test
    public void testCur() {
        analyseStagnationPoint.analyzeStagnationPoint();
    }
}
