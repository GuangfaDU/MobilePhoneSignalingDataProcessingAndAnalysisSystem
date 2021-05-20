package cn.edu.szu.mail.service;

import cn.edu.szu.mail.entity.RequestMail;
import cn.edu.szu.service.ICrudService;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IRequestMailService extends ICrudService<RequestMail> {

    /**
     * 处理申请信息
     * @param requestMail 申请信息
     * @return 处理操作是否被成功执行
     */
    boolean handleRegistry(RequestMail requestMail);
}
