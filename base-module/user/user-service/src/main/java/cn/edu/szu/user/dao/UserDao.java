package cn.edu.szu.user.dao;

import cn.edu.szu.dao.ICrudDao;
import cn.edu.szu.user.entity.User;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface UserDao extends ICrudDao<User> {

    /**
     * 根据用户名查询用户
     * @param userName 指定的用户名
     * @return 用户实例
     */
    User queryByUserName(String userName);
}
