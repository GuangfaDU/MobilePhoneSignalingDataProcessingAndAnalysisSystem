package cn.edu.szu.algo.component.pretreatment;

import cn.edu.szu.algo.client.FileServiceClient;
import cn.edu.szu.algo.component.density.ComputeDensityImpl;
import cn.edu.szu.algo.dao.SignalDataDao;
import cn.edu.szu.algo.entity.BaseStation;
import cn.edu.szu.algo.interfaces.DefaultParams;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.interfaces.pretreatment.IImportData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class ImportDataImpl extends BaseUtilImpl implements DefaultParams, IImportData {

    private final SignalDataDao signalDataDao;
    private final MongoTemplate mongoTemplate;
    private final ComputeDensityImpl computeDensity;

    @Autowired
    public ImportDataImpl(FileServiceClient fileServiceClient, ObjectMapper objectMapper,
                          SignalDataDao signalDataDao, MongoTemplate mongoTemplate,
                          ComputeDensityImpl computeDensity) {
        super(fileServiceClient, objectMapper);
        this.signalDataDao = signalDataDao;
        this.mongoTemplate = mongoTemplate;
        this.computeDensity = computeDensity;
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void importData() {
        HashSet<String> fileListSet = getFileList();
        fileListSet.forEach(fileName -> {
            if (fileName.contains("-pretreated") && !fileListSet.contains(fileName + ".done")) {
                importIntoDatabase(fileName);
            }
        });
    }

    private void importIntoDatabase(String fileName) {
        ensureFileExists(fileName);

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, BaseStation.class);

        List<SignalData> signalDataList = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(DEFAULT_DOWNLOAD_DIRECTORY + fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");
                SignalData data = new SignalData();
                data.setImsi(items[0])
                        .setStart(items[1])
                        .setEnd(items[2])
                        .setLongitude(Double.parseDouble(items[3]))
                        .setLatitude(Double.parseDouble(items[4]));

                computeDensity.computeDensity(data);

                Query query = new Query()
                        .addCriteria(Criteria.where("longitude").is(data.getLongitude()))
                        .addCriteria(Criteria.where("latitude").is(data.getLatitude()));
                Update update = new Update();
                update.set("longitude", data.getLongitude());
                update.set("latitude", data.getLatitude());
                bulkOperations.upsert(query, update);

                signalDataList.add(data);

                if (signalDataList.size() >= 10000) {
                    signalDataDao.insertBatch(signalDataList);
                    signalDataList.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        signalDataDao.insertBatch(signalDataList);
        bulkOperations.execute();

        uploadFile(fileName);
    }

    @Override
    public void uploadFile(String fileName) {
        File file = new File(DEFAULT_UPLOAD_DIRECTORY + fileName + ".done");
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException("创建标记文件失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.uploadFile(fileName + ".done");
    }
}
