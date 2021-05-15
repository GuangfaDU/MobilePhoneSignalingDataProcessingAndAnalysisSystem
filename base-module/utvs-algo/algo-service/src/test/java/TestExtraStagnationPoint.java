import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.stagnation.ExtraStagnationPointImpl;
import cn.edu.szu.algo.entity.AggregationResultMap;
import cn.edu.szu.algo.entity.StagnationResultMap;
import cn.edu.szu.algo.interfaces.stagnation.IExtraStagnationPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgoServiceApplication.class)
public class TestExtraStagnationPoint {

    @Autowired
    ExtraStagnationPointImpl extraStagnationPoint;
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testExtraStagnationPoint() {
//        AggregationResultMap resultMap = mongoTemplate.findOne(
//                new Query()
//                        .addCriteria(Criteria.where("imsi").is("168385E9E4BC6A749D069EB39DDAAD12")),
//                AggregationResultMap.class);
//        assert resultMap != null;
//        extraStagnationPoint.extraStagnationPoint(resultMap);
        extraStagnationPoint.extraStagnationPoint();
    }

    @Test
    public void showData() {
        mongoTemplate.findAll(StagnationResultMap.class).forEach(System.out::println);
    }
}
