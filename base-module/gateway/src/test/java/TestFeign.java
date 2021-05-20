import cn.edu.szu.gateway.GatewayApplication;
import cn.edu.szu.gateway.client.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
public class TestFeign {

    @Autowired
    private UserClient userClient;

    @Test
    public void test() {
        System.out.println(userClient.queryByName("utvs_00002"));
    }

}
