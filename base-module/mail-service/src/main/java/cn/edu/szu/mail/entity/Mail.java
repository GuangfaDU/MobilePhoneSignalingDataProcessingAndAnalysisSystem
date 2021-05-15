package cn.edu.szu.mail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
public class Mail {

    /**
     * 邮件id
     */
    private String id;
    /**
     * 发送人
     */
    private String from;
    /**
     * 接收人
     */
    private String to;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 发送日期
     */
    private Date sendDate;
    /**
     * 抄送
     */
    private String cc;
    /**
     * 密送
     */
    private String bcc;
    /**
     * 附件
     */
    @JsonIgnore
    private MultipartFile[] multipartFiles;
}
