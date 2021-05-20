package cn.edu.szu.mail.util.mail;

import cn.edu.szu.mail.entity.Mail;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.Objects;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class MailTemplate {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public boolean sendMail(Mail mail) {
        try {
            checkMail(mail);
            buildAndSendMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void checkMail(Mail mail) {
        if (StringUtils.isEmpty(mail.getTo())) {
            throw new IllegalArgumentException("邮件收件人不能为空");
        }

        if (StringUtils.isEmpty(mail.getSubject())) {
            throw new IllegalArgumentException("邮件主题不能为空");
        }

        if (StringUtils.isEmpty(mail.getContent())) {
            throw new IllegalArgumentException("邮件内容不能为空");
        }
    }

    private void buildAndSendMail(Mail mail) {
        try {
            MimeMessageHelper helper = parseMail(
                    new MimeMessageHelper(mailSender.createMimeMessage(), true), mail);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private MimeMessageHelper parseMail(MimeMessageHelper helper, Mail mail) throws MessagingException {
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());

        if (!StringUtils.isEmpty(mail.getCc())) {
            helper.setCc(mail.getCc().split(";"));
        }

        if (!StringUtils.isEmpty(mail.getBcc())) {
            helper.setBcc(mail.getBcc().split(";"));
        }

        if (mail.getSendDate() != null) {
            helper.setSentDate(mail.getSendDate());
        }

        if (mail.getMultipartFiles() != null && mail.getMultipartFiles().length != 0) {
            for (MultipartFile multipartFile : mail.getMultipartFiles()) {
                helper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
            }
        }

        return helper;
    }
}
