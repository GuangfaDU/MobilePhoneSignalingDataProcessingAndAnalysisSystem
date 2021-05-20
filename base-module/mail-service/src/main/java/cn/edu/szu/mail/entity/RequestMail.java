package cn.edu.szu.mail.entity;

import cn.edu.szu.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mail")
public class RequestMail extends BaseEntity {

    /**
     * 申请人姓名
     */
    @TableField("name")
    private String name;
    /**
     * 申请账号身份
     */
    @TableField("role")
    private String role;
    /**
     * 申请人邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 是否同意申请操作，同意为1，否则为0
     */
    @TableField("operation")
    private Integer operation;

    /**
     * 申请备注
     */
    @TableField("note")
    private String note;
}
