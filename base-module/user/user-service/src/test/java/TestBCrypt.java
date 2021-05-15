import cn.edu.szu.mail.util.user.BCrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBCrypt.class)
public class TestBCrypt {

    @Test
    public void testBCrypt() {
        String password = "123456";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(BCrypt.checkpw(password, hashed));
    }
}
