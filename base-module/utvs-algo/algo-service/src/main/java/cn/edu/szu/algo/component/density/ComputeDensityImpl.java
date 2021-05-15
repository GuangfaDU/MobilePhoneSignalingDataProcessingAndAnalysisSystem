package cn.edu.szu.algo.component.density;

import cn.edu.szu.algo.dao.SignalDataDao;
import cn.edu.szu.algo.entity.BaseStation;
import cn.edu.szu.algo.entity.DensityResultMap;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.interfaces.density.IComputeDensity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class ComputeDensityImpl implements IComputeDensity {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ComputeDensityImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void computeDensity(SignalData signalData) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, DensityResultMap.class);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime localDateTime = LocalDateTime.parse(signalData.getStart(), dateTimeFormatter);
        LocalDateTime parsedStartTime = LocalDateTime.of(localDateTime.getYear(),
                localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), 0, 0);
        LocalDateTime parsedEndTime = parsedStartTime.plusHours(1L);

        Query query = new Query();
        query
                .addCriteria(Criteria.where("endTime").is(parsedEndTime.format(dateTimeFormatter)))
                .addCriteria(Criteria.where("latitude").is(signalData.getLatitude()))
                .addCriteria(Criteria.where("longitude").is(signalData.getLongitude()))
                .addCriteria(Criteria.where("startTime").is(parsedStartTime.format(dateTimeFormatter)))
        ;
        Update update = new Update();
        update.inc("density", 1);
        bulkOperations.upsert(query, update);


        bulkOperations.execute();
    }
}
