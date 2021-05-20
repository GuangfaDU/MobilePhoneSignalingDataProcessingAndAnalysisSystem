import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.AlgoServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgoServiceApplication.class)
public class TestMongoDB {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSave() {
        List<SignalData> signalDataList = new LinkedList<>();
        signalDataList.add(new SignalData()
                .setImsi("79128A03E8C0A022ED68821F7F7ABE86")
                .setStart("20200802133613")
                .setEnd("20200802133613")
                .setLongitude(113.90569)
                .setLatitude(22.79983));
        signalDataList.add(new SignalData()
                .setImsi("79128A03E8C0A022ED68821F7F7ABE86")
                .setStart("20200802145934")
                .setEnd("20200802145934")
                .setLongitude(113.8943464)
                .setLatitude(22.80475833));

        Update update = new Update();
        update.set("signalDataList", signalDataList);
        Query query = new Query();
        query.addCriteria(Criteria.where("imsi").is("79128A03E8C0A022ED68821F7F7ABE86"));
        mongoTemplate.upsert(query, update, AggregationResultMap.class);
    }

    @Test
    public void testFind() {
        Query query = new Query();
        query.addCriteria(Criteria.where("signalDataList").size(5));
    }

    @Test
    public void testDelete() {
        String imsi = "79128A03E8C0A022ED68821F7F7ABE86";
        mongoTemplate.remove(new Query(Criteria.where("imsi").is(imsi)), AggregationResultMap.class);
    }
}
