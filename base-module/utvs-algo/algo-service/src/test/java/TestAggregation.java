import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.pretreatment.AggregationImpl;
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
public class TestAggregation {

    @Autowired
    AggregationImpl andAggregation;

    @Test
    public void testAggreration() {
        andAggregation.aggregation();
    }

//    @Test
//    public void removeDuplicates() {
//        List<SignalData> list = andAggregation.getSignalDataByImsi(new SignalData().setImsi("79128A03E8C0A022ED68821F7F7ABE86"));
//        list.forEach(System.out::println);
//        System.out.println();
//        System.out.println();
//        andAggregation.aggregation(list).forEach(System.out::println);
//    }
//
//    @Test
//    public void testAggregation() {
//        List<SignalData> list = new LinkedList<>();
//        list.add(new SignalData()
//                .setImsi("79128A03E8C0A022ED68821F7F7ABE86")
//                .setStart("20200802133613")
//                .setEnd("20200802133613")
//                .setLongitude(113.90569)
//                .setLatitude(22.79983));
//        list.add(new SignalData()
//                .setImsi("79128A03E8C0A022ED68821F7F7ABE86")
//                .setStart("20200802133813")
//                .setEnd("20200802133813")
//                .setLongitude(113.0569)
//                .setLatitude(22.79983));
//        list.add(new SignalData()
//                .setImsi("79128A03E8C0A022ED68821F7F7ABE86")
//                .setStart("20200802133913")
//                .setEnd("20200802134013")
//                .setLongitude(113.90569)
//                .setLatitude(22.79983));
//        andAggregation.removeDriftData(list).forEach(System.out::println);
//    }

//    @Test
//    public void checkTime() {
//        System.out.println(andAggregation.checkTimestamp("2020-08-01 20:22:47", "2020-08-01 19:51:51"));
//    }
}
