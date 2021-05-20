package cn.edu.szu.user.service;

import cn.edu.szu.service.ICrudService;
import cn.edu.szu.user.entity.User;

import java.util.List;

/**
 * @author GuangfaDu
 * @version 1.0
 */
public interface IUserService extends ICrudService<User> {

    /**
     * 根据用户名查询指定用户
     * @param userName 指定用户名
     * @return 用户实例
     */
    User queryByName(String userName);

    /**
     * 动态SQL查询
     * @param username 用户名
     * @param email 邮箱
     * @return 用户列表
     */
    List<User> dynamicQuery(String username, String email);
}
