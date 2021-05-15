import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.trajectory.ExtraTravelTrajectoryImpl;
import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.TravelTrajectoryResultMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgoServiceApplication.class)
public class TestExtraTrajectory {

    @Autowired
    ExtraTravelTrajectoryImpl extraTravelTrajectory;
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testExtraTrajectory() {
        extraTravelTrajectory.extraTravelTrajectory();
//        AggregationResultMap one = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("imsi").is("168385E9E4BC6A749D069EB39DDAAD12")),
//                AggregationResultMap.class);
//        assert one != null;
//        extraTravelTrajectory.extraTravelTrajectory(one);
    }

    @Test
    public void showTrajectory() {
        mongoTemplate.findAll(TravelTrajectoryResultMap.class).forEach(System.out::println);
    }

    @Test
    public void showData() {
        mongoTemplate.findAll(AggregationResultMap.class).forEach(System.out::println);
    }

//    @Test
//    public void testTimeCompute() {
//        System.out.println(extraTravelTrajectory.checkTimestamp("2020-08-01 20:22:47", "2020-08-01 19:51:51"));
//    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
    }
}
