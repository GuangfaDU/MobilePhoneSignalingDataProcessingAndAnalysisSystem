package cn.edu.szu.user.entity;

import cn.edu.szu.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
@CrossOrigin(exposedHeaders = {"Authorization"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class User extends BaseEntity {

    /**
     * 用户名
     */
    @TableField("name")
    private String username;
    /**
     * 用户密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户对应的角色id
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 角色描述
     */
    @TableField(exist = false)
    private Role roleDesc;
    /**
     * 用户个人邮箱
     */
    private String email;
    /**
     * 账号创建时间
     */
    @TableField(value = "register_time")
    private Timestamp registerTime;
    /**
     * 最近一次登陆时间
     */
    @TableField(value = "last_login_time")
    private Timestamp lastLoginTime;
    /**
     * 账号状态： {可用，不可用}
     */
    private String status;
    /**
     * 散列密码加上的盐
     */
    @TableField(value = "salt")
    @JsonIgnore
    private String salt;
}
