package cn.edu.szu.mail.service.impl;

import cn.edu.szu.mail.entity.RequestMail;
import cn.edu.szu.mail.service.IMailService;
import cn.edu.szu.mail.service.IRequestMailService;
import cn.edu.szu.mail.util.mail.MailParser;
import cn.edu.szu.service.impl.CrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Service
public class RequestMailImpl extends CrudServiceImpl<RequestMail> implements IRequestMailService {

    @Autowired
    IMailService mailService;

    @Override
    public boolean handleRegistry(RequestMail requestMail) {
        try {
            mailService.sendMail(MailParser.parseMail(requestMail));
            getBaseMapper().deleteById(requestMail.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
