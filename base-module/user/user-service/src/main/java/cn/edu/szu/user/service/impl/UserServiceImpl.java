package cn.edu.szu.user.service.impl;

import cn.edu.szu.service.impl.CrudServiceImpl;
import cn.edu.szu.user.dao.RoleDao;
import cn.edu.szu.user.dao.UserDao;
import cn.edu.szu.user.entity.Role;
import cn.edu.szu.user.entity.User;
import cn.edu.szu.user.service.IUserService;
import cn.edu.szu.user.util.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements IUserService {

    @Autowired
    RoleDao roleDao;

    @Override
    public boolean saveOrUpdate(User entity) {
        if (entity.getId() == null) {
            String salt = BCrypt.gensalt();
            entity.setPassword(BCrypt.hashpw(entity.getPassword(), salt));
            entity.setSalt(salt);
            entity.setRegisterTime(new Timestamp(System.currentTimeMillis()));
            entity.setStatus("available");
            return getBaseMapper().insert(entity) != 0;
        }

        if (entity.getPassword() != null) {
            String plainText = entity.getPassword();
            String salt = getBaseMapper().selectById(entity.getId()).getSalt();
            entity.setPassword(BCrypt.hashpw(plainText, salt));
        }

        return getBaseMapper().updateById(entity) != 0;
    }

    @Override
    public IPage<User> listByPage(User entity, Long pageNum, Long pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (entity.getId() != null) {
            queryWrapper.eq("id", entity.getId());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            queryWrapper.like("name", entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getEmail())) {
            queryWrapper.like("email", entity.getEmail());
        }
        if (entity.getRegisterTime() != null) {
            queryWrapper.le("register_time", entity.getRegisterTime());
        }
        if (entity.getLastLoginTime() != null) {
            queryWrapper.le("last_login_time", entity.getLastLoginTime());
        }

        return getBaseMapper().selectPage(page, queryWrapper);
    }

    @Override
    public List<User> dynamicQuery(String username, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like("name", username);
        }
        if (!StringUtils.isEmpty(email)) {
            queryWrapper.like("email", email);
        }

        List<User> users = getBaseMapper().selectList(queryWrapper);
        List<Role> roles = roleDao.selectList(Wrappers.emptyWrapper());
        users.forEach(user -> {
            roles.forEach(role -> {
                if (user.getRoleId().equals(role.getId())) {
                    user.setRoleDesc(role);
                }
            });
        });

        return users;
    }

    @Override
    public User queryByName(String userName) {
        return ((UserDao) getBaseMapper()).queryByUserName(userName);
    }
}
