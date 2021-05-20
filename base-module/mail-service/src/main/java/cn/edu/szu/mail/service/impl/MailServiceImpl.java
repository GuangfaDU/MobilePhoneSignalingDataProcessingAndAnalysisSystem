package cn.edu.szu.mail.service.impl;

import cn.edu.szu.mail.entity.Mail;
import cn.edu.szu.mail.service.IMailService;
import cn.edu.szu.mail.util.mail.MailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Service
public class MailServiceImpl implements IMailService {

    private static final String DEFAULT_FROM_MAIL = "szu_utvs@163.com";

    @Autowired
    private MailTemplate mailTemplate;

    @Override
    public boolean sendMail(Mail mail) {
        if (mail.getFrom() == null) {
            mail.setFrom(DEFAULT_FROM_MAIL);
        }

        if (mail.getSendDate() == null) {
            mail.setSendDate(new Date());
        }

        return mailTemplate.sendMail(mail);
    }
}
