package cn.edu.szu.mail.Controller;

import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.mail.entity.Mail;
import cn.edu.szu.mail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
public class MailController {

    @Autowired
    private IMailService mailService;

    @PostMapping("/send")
    public ResponseEntity sendMail(Mail mail) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (mailService.sendMail(mail)) {
            responseEntity.setSuccess(true).setMessage("邮件发送成功");
        }

        return responseEntity.setSuccess(false).setMessage("邮件发送失败");
    }
}
