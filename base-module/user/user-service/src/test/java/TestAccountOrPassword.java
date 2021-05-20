import cn.edu.szu.mail.util.user.GenerateUserAccount;
import cn.edu.szu.mail.util.user.SecureRandomPassword;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestAccountOrPassword.class)
public class TestAccountOrPassword {

    @Test
    public void testGenerateAccount() {
        System.out.println(GenerateUserAccount.getInstance().generateUserAccount());
    }

    @Test
    public void testRandomPassword() {
        System.out.println(SecureRandomPassword.secureRandomPassword(15));
    }
}
