import cn.edu.szu.mail.MailServiceApplication;
import cn.edu.szu.mail.entity.RequestMail;
import cn.edu.szu.mail.service.IRequestMailService;
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
public class TestRequestMailCurd {

    @Autowired
    IRequestMailService requestMailService;

    @Test
    public void testSave() {
        RequestMail mail = new RequestMail().setName("test").setEmail("test@123.com").setRole("ROOT");
        requestMailService.save(mail);
    }

    @Test
    public void testDelete() {
        requestMailService.removeById(1L);
    }
}
