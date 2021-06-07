package cn.edu.szu.user.entity;

import cn.edu.szu.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "role")
public class Role extends BaseEntity {
    /**
     * 角色名称
     */
    @TableField("rname")
    private String roleName;
    /**
     * 角色描述
     */
    @TableField("description")
    private String description;
}
