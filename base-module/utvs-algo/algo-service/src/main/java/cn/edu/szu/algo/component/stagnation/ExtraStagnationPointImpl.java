package cn.edu.szu.algo.component.stagnation;

import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.Point;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.entity.StagnationResultMap;
import cn.edu.szu.algo.interfaces.stagnation.IExtraStagnationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static cn.edu.szu.algo.util.ComputeTimestamp.*;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class ExtraStagnationPointImpl implements IExtraStagnationPoint {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ExtraStagnationPointImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void extraStagnationPoint() {
        mongoTemplate.findAll(AggregationResultMap.class).forEach(this::extraStagnationPoint);
    }

    public void extraStagnationPoint(AggregationResultMap resultMap) {
        List<SignalData> signalDataList = resultMap.getSignalDataList();
        if (signalDataList.size() <= 1) {
            return;
        }

        List<StagnationResultMap> stagnationList = new LinkedList<>();
        for (int i = 0; i < signalDataList.size() - 1; ++i) {
            SignalData startData = signalDataList.get(i);
            SignalData endData = signalDataList.get(i + 1);
            if (checkTimestamp(endData.getStart(), startData.getStart()) >= (30 * 60 * 1000)) {
                stagnationList.add(new StagnationResultMap()
                        .setImsi(startData.getImsi())
                        .setStartTime(startData.getStart())
                        .setEndTime(endData.getStart())
                        .setStagnationPoint(new Point()
                                .setLatitude(startData.getLatitude())
                                .setLongitude(startData.getLongitude())));
            }
        }

        if (stagnationList.size() != 0) {
            saveToMongoDb(stagnationList);
        }
    }

    private void saveToMongoDb(List<StagnationResultMap> stagnationList) {
        BulkOperations bulkOperations =
                mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, StagnationResultMap.class);

        stagnationList.forEach(stagnationResultMap -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("imsi").is(stagnationResultMap.getImsi()))
                    .addCriteria(Criteria.where("startTime").is(stagnationResultMap.getStartTime()))
                    .addCriteria(Criteria.where("endTime").is(stagnationResultMap.getEndTime()));
            Update update = new Update();
            update.set("stagnationPoint", stagnationResultMap.getStagnationPoint());
            bulkOperations.upsert(query, update);
        });

        bulkOperations.execute();
    }
}
