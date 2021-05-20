package cn.edu.szu.algo.component.trajectory;

import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.Point;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.entity.TravelTrajectoryResultMap;
import cn.edu.szu.algo.interfaces.trajectory.IExtraTravelTrajectory;
import cn.edu.szu.algo.util.ComputeDistance;
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
public class ExtraTravelTrajectoryImpl implements IExtraTravelTrajectory {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ExtraTravelTrajectoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void extraTravelTrajectory() {
        List<AggregationResultMap> resultMaps = mongoTemplate.findAll(AggregationResultMap.class);
        resultMaps.forEach(this::extraTravelTrajectory);
    }

    public void extraTravelTrajectory(AggregationResultMap resultMap) {
        List<SignalData> signalDataList = resultMap.getSignalDataList();

        LinkedList<LinkedList<SignalData>> result = new LinkedList<>();
        LinkedList<SignalData> trajectoryList = new LinkedList<>();
        trajectoryList.add(signalDataList.get(0));
        for (int i = 1; i < signalDataList.size(); ++i) {
            if (checkTimestamp(signalDataList.get(i).getStart(), trajectoryList.getLast().getStart()) < 2400000) {
                trajectoryList.add(signalDataList.get(i));
                continue;
            }

            result.add(new LinkedList<>(trajectoryList));
            trajectoryList.clear();
            trajectoryList.add(signalDataList.get(i));
        }

        if (signalDataList.size() <= 1) {
            result.add(trajectoryList);
        }

        removeShortTrajectory(result);

        if (result.size() != 0) {
            saveToMongoDb(resultMap.getImsi(), result);
        }
    }

    private void removeShortTrajectory(LinkedList<LinkedList<SignalData>> result) {
        for (int i = 0; i < result.size(); ++i) {
            LinkedList<SignalData> iData = result.get(i);

            if (iData.size() <= 1) {
                continue;
            }

            double lng1 = iData.getFirst().getLongitude();
            double lat1 = iData.getFirst().getLatitude();
            double lng2 = iData.getLast().getLongitude();
            double lat2 = iData.getLast().getLatitude();
            if (ComputeDistance.getDistance(lat1, lng1, lat2, lng2) < 500 ||
                    checkTimestamp(iData.getLast().getStart(), iData.getFirst().getStart()) < 300000) {
                result.remove(i);
                --i;
            }
        }
    }

    private void saveToMongoDb(String imsi, LinkedList<LinkedList<SignalData>> result) {
        BulkOperations bulkOperations =
                mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, TravelTrajectoryResultMap.class);

        result.forEach(pointList -> {
            TravelTrajectoryResultMap resultMap = new TravelTrajectoryResultMap();
            resultMap.setImsi(imsi);
            resultMap.setPointList(pointList);
            SignalData startData = pointList.getFirst();
            SignalData endData = pointList.getLast();
            resultMap.setStartTime(startData.getStart());
            resultMap.setEndTime(endData.getStart());
            resultMap.setStartPoint(new Point()
                    .setLongitude(startData.getLongitude())
                    .setLatitude(startData.getLatitude()));
            resultMap.setEndPoint(new Point()
                    .setLongitude(endData.getLongitude())
                    .setLatitude(endData.getLatitude()));

            Query query = new Query();
            query.addCriteria(Criteria.where("imsi").is(imsi));
            query.addCriteria(Criteria.where("startTime").is(resultMap.getStartTime()));
            query.addCriteria(Criteria.where("endTime").is(resultMap.getEndTime()));
            query.addCriteria(Criteria.where("startPoint").is(resultMap.getStartPoint()));
            query.addCriteria(Criteria.where("endPoint").is(resultMap.getEndPoint()));
            Update update = new Update();
            update.set("poi", pointList);
            bulkOperations.upsert(query, update);
        });

        bulkOperations.execute();
    }
}
