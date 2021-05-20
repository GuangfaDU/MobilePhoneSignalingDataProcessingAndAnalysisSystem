package cn.edu.szu.mail.Controller;

import cn.edu.szu.controller.BaseController;
import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.mail.entity.RequestMail;
import cn.edu.szu.mail.service.IRequestMailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
public class RequestMailController extends BaseController<IRequestMailService, RequestMail> {

    @PostMapping("/registry")
    public ResponseEntity registry(RequestMail requestMail) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (service.save(requestMail)) {
            return responseEntity.setSuccess(true).setMessage("申请登记成功");
        }

        return responseEntity.setSuccess(false).setMessage("申请登记失败, 请稍后重试");
    }

    @PostMapping("/handle")
    public ResponseEntity handleRegistry(RequestMail requestMail) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (service.handleRegistry(requestMail)) {
            return responseEntity.setSuccess(true).setMessage("操作成功");
        }

        return responseEntity.setSuccess(false).setMessage("操作失败");
    }
}
