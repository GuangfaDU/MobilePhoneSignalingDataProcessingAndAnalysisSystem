import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.pretreatment.AggregationImpl;
import cn.edu.szu.algo.dao.SignalDataDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgoServiceApplication.class)
public class TestSignalDataCrud {

    @Autowired
    SignalDataDao signalDataDao;
    @Autowired
    AggregationImpl andAggregation;

    @Test
    public void testSelect() {
        signalDataDao.selectList(Wrappers.emptyWrapper()).forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        SignalData signalData = new SignalData();
        signalData.setImsi("79128A03E8C0A022ED68821F7F7ABE86")
                .setStart("20200802133613")
                .setEnd("20200802133613")
                .setLongitude(113.90569)
                .setLatitude(22.79983);
        signalDataDao.insert(signalData);
    }

    @Test
    public void testGroupBy() {
        QueryWrapper<SignalData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("imsi").groupBy("imsi");
        signalDataDao.selectList(queryWrapper).forEach(System.out::println);
    }
}
