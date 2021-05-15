import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.pretreatment.ImportDataImpl;
import cn.edu.szu.algo.entity.DensityResultMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class TestImportDatabase {

    @Autowired
    ImportDataImpl importIntoDatabase;
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testImportData() {
        importIntoDatabase.importData();
    }

    @Test
    public void showDensity() {
        Query query = new Query();
        query.addCriteria(Criteria.where("startTime").gt("20200820153025"));
        mongoTemplate.find(query, DensityResultMap.class).forEach(System.out::println);
    }
}
