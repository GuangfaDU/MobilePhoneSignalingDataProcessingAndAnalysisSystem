package cn.edu.szu.mail.util.mail;

import cn.edu.szu.mail.entity.Mail;
import cn.edu.szu.mail.entity.RequestMail;
import cn.edu.szu.mail.util.user.GenerateUserAccount;
import cn.edu.szu.mail.util.user.SecureRandomPassword;
import cn.edu.szu.mail.util.user.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class MailParser {

    private static UserUtil userUtil;

    @Autowired
    public MailParser(UserUtil userUtil) {
        MailParser.userUtil = userUtil;
    }


    public static final int FIXED_PASSWORD_LENGTH = 15;

    public static final String TEMPLATE_SUBJECT = "申请UTVS系统账号结果通知";
    public static final String TEMPLATE_AGREE_CONTENT =
            "恭喜您，您的本次账号申请已通过:\n" +
            "账号: %s\t" +
            "密码: %s\t" +
            "\n\n您的登陆账号与密码非常重要, 请妥善保存。首次登陆后可通过密码修改功能来自定义登陆密码。\n" +
            "其余问题可通过邮箱cherygong@outlook.com与我们联系，感谢您对本系统的支持与关注!";
    public static final String TEMPLATE_AGAINST_CONTENT =
            "很抱歉，您的本次账号申请未通过。您可以尝试以下步骤来提高申请通过率:\n" +
            "\t1. 申请更低权限的账号身份\n" +
            "\t2. 提供准确的申请姓名\n" +
            "\t3. 提供更详细的申请备注\n" +
            "\n\n感谢您对于本系统的支持与关注，有问题请通过邮箱cherygong@outlook.com来联系我们!";

    public static String fillAgreeTemplate(RequestMail requestMail) {
        String username = GenerateUserAccount.getInstance().generateUserAccount();
        String password = SecureRandomPassword.secureRandomPassword(FIXED_PASSWORD_LENGTH);
        String filledTemplate = String.format(TEMPLATE_AGREE_CONTENT, username, password);

        if (!userUtil.saveUser(username, password, requestMail)) {
            throw new RuntimeException("用户远程服务调用异常");
        }

        return filledTemplate;
    }

    public static Mail parseMail(RequestMail requestMail) {
        String to = requestMail.getEmail();
        Mail mail = new Mail();
        mail.setTo(to);
        mail.setSubject(TEMPLATE_SUBJECT);
        if (requestMail.getOperation() == 1) {
            mail.setContent(fillAgreeTemplate(requestMail));
            return mail;
        }

        mail.setContent(TEMPLATE_AGAINST_CONTENT);
        return mail;
    }
}
