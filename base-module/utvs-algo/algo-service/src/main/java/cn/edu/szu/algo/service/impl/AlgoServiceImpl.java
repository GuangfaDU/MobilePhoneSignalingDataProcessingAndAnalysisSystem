package cn.edu.szu.algo.service.impl;

import cn.edu.szu.algo.entity.*;
import cn.edu.szu.algo.service.IAlgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Service
public class AlgoServiceImpl implements IAlgoService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AlgoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<TravelTrajectoryResultMap> getAllTravelTrajectory(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Query query = new Query();
        query.addCriteria(Criteria.where("startTime").gte(parseDateTime(startTime, formatter)))
                .addCriteria(Criteria.where("endTime").lte(parseDateTime(endTime, formatter)));
        return mongoTemplate.find(query, TravelTrajectoryResultMap.class);
    }

    @Override
    public List<DensityResultMap> getDensity(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        Query query = new Query()
                .addCriteria(Criteria.where("startTime").gte(parseDateTime(startTime, formatter)))
                .addCriteria(Criteria.where("endTime").lte(parseDateTime(endTime, formatter)));
        List<DensityResultMap> resultMaps = mongoTemplate.find(query, DensityResultMap.class);
        HashMap<Point, Long> mapReduce = new HashMap<>();
        resultMaps.forEach(densityResultMap -> {
            Point point = new Point()
                    .setLongitude(densityResultMap.getLongitude())
                    .setLatitude(densityResultMap.getLatitude());
            if (mapReduce.containsKey(point)) {
                mapReduce.put(point, mapReduce.get(point) + densityResultMap.getDensity());
            } else {
                mapReduce.put(point, densityResultMap.getDensity());
            }
        });

        List<DensityResultMap> finalResult = new LinkedList<>();
        mapReduce.forEach((point, density) -> {
            finalResult.add(new DensityResultMap()
            .setLongitude(point.getLongitude())
            .setLatitude(point.getLatitude())
            .setDensity(density));
        });

        return finalResult;
    }

    @Override
    public List<StagnationResultMap> getStagnation(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Query query = new Query();
        query.addCriteria(Criteria.where("startTime").gte(parseDateTime(startTime, formatter)))
                .addCriteria(Criteria.where("endTime").lte(parseDateTime(endTime, formatter)));
        return mongoTemplate.find(query, StagnationResultMap.class);
    }

    @Override
    public List<ClusteringResultMap> getPoi() {
        return mongoTemplate.find(new Query(), ClusteringResultMap.class);
    }

    private String parseDateTime(String time, DateTimeFormatter formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime parseTime = LocalDateTime.parse(time, dateTimeFormatter);
        return formatter.format(parseTime);
    }
}
