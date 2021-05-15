import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.component.pretreatment.RemoveIllegalImpl;
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
public class TestRemoveIllegalImpl {

    @Autowired
    RemoveIllegalImpl removeIllegal;

    @Test
    public void testRemoveIllegal() {
        removeIllegal.removeIllegal();
    }
}
