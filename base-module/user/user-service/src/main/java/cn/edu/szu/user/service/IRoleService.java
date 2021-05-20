package cn.edu.szu.user.service;

import cn.edu.szu.service.ICrudService;
import cn.edu.szu.user.entity.Role;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IRoleService extends ICrudService<Role> {

    /**
     * 根据角色名称查询角色
     * @param name 角色名称
     * @return 角色
     */
    Role queryByName(String name);
}
