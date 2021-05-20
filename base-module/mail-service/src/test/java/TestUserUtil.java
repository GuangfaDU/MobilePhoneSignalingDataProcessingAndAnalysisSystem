import cn.edu.szu.mail.MailServiceApplication;
import cn.edu.szu.mail.client.UserClient;
import cn.edu.szu.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@SpringBootTest(classes = MailServiceApplication.class)
public class TestUserUtil {

    @Autowired
    private UserClient userClient;

    @Test
    public void testGetRoleId() {
        System.out.println(userClient.queryByName("STANDARD"));
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@123.com");
        userClient.save(user);
    }
}
