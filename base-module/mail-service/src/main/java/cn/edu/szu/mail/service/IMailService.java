package cn.edu.szu.mail.service;

import cn.edu.szu.mail.entity.Mail;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IMailService {

    /**
     * 发送邮件
     * @param mail 邮件实体类
     * @return 发送是否成功
     */
    boolean sendMail(Mail mail);
}
