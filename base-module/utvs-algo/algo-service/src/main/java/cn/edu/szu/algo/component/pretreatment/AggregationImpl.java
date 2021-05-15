package cn.edu.szu.algo.component.pretreatment;

import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.dao.SignalDataDao;
import cn.edu.szu.algo.interfaces.pretreatment.IAggregation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static cn.edu.szu.algo.util.ComputeDistance.*;
import static cn.edu.szu.algo.util.ComputeTimestamp.*;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class AggregationImpl implements IAggregation {

    private final SignalDataDao signalDataDao;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public AggregationImpl(SignalDataDao signalDataDao, MongoTemplate mongoTemplate) {
        this.signalDataDao = signalDataDao;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Scheduled(cron = "0 0 6 * * ?")
    public void aggregation() {
        getImsiList().forEach(signalData -> {
            List<SignalData> res = getSignalDataByImsi(signalData);
            res = aggregation(res);
            removePingPongData(res);
            removeDriftData(res);
            saveToMongoDb(signalData, res);
        });
    }

    private void saveToMongoDb(SignalData signalData, List<SignalData> signalDataList) {
        Update update = new Update();
        update.set("signalDataList", signalDataList);
        Query query = new Query();
        query.addCriteria(Criteria.where("imsi").is(signalData.getImsi()));
        mongoTemplate.upsert(query, update, AggregationResultMap.class);
    }

    private List<SignalData> getImsiList() {
        QueryWrapper<SignalData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("imsi").groupBy("imsi");
        return signalDataDao.selectList(queryWrapper);
    }

    public List<SignalData> getSignalDataByImsi(SignalData signalData) {
        QueryWrapper<SignalData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("imsi", signalData.getImsi()).orderByAsc("start");
        return signalDataDao.selectList(queryWrapper);
    }

    private List<SignalData> aggregation(List<SignalData> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<SignalData> res = new LinkedList<>();
        int slow = 0, fast = 1;
        res.add(list.get(slow));
        while (fast < list.size()) {
            SignalData slowData = list.get(slow);
            SignalData fastData = list.get(fast);
            if (slowData.getLongitude().equals(fastData.getLongitude()) &&
                    slowData.getLatitude().equals(fastData.getLatitude())) {
                ++fast;
                continue;
            }

            slow = fast;
            ++fast;
            res.add(list.get(slow));
        }

        return res;
    }

    private void removePingPongData(List<SignalData> list) {
        if (list.size() < 3) {
            return;
        }

        int slow = 0, fast = slow + 2;
        while (fast < list.size()) {
            SignalData slowData = list.get(slow);
            SignalData fastData = list.get(fast);
            if (slowData.getLongitude().equals(fastData.getLongitude()) &&
                    slowData.getLatitude().equals(fastData.getLatitude()) &&
                    checkTimestamp(fastData.getStart(), slowData.getStart()) < 600000) {
                slowData.setEnd(fastData.getEnd());
                list.remove(slow + 1);
                list.remove(slow + 1);
                continue;
            }

            ++slow;
            fast = slow + 2;
        }
    }

    private void removeDriftData(List<SignalData> list) {
        if (list.size() < 3) {
            return;
        }

        int slow = 0, fast = slow + 2;
        while (fast < list.size()) {
            SignalData data1 = list.get(slow);
            SignalData data2 = list.get(slow + 1);
            SignalData data3 = list.get(fast);

            double distance1T2 =
                    getDistance(data1.getLatitude(), data1.getLongitude(), data2.getLatitude(), data2.getLongitude());
            double distance2T3 =
                    getDistance(data2.getLatitude(), data2.getLongitude(), data3.getLatitude(), data3.getLongitude());
            double distance1T3 =
                    getDistance(data1.getLatitude(), data1.getLongitude(), data3.getLatitude(), data3.getLongitude());

            boolean checkDistance = (distance1T2 + distance2T3) > (3 * distance1T3);
            boolean checkSpeed = ((distance1T2 + distance2T3) /
                    checkTimestamp(data3.getStart(), data1.getStart())) > (110 / 3.6);
            if (checkDistance && checkSpeed) {
                list.remove(slow + 1);
                continue;
            }

            ++slow;
            fast = slow + 2;
        }
    }
}
