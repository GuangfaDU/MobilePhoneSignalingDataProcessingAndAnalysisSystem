import cn.edu.szu.mail.MailServiceApplication;
import cn.edu.szu.mail.entity.Mail;
import cn.edu.szu.mail.service.IMailService;
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
public class TestSendMail {

    @Autowired
    private IMailService mailService;

    @Test
    public void testSendMail() {
        Mail mail = new Mail();
        mail.setSubject("Test");
        mail.setContent("Test");
        mail.setTo("2017153016@email.szu.edu.cn");
        mailService.sendMail(mail);
    }
}
