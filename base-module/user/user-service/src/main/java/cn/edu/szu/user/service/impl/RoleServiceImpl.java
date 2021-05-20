package cn.edu.szu.user.service.impl;

import cn.edu.szu.service.impl.CrudServiceImpl;
import cn.edu.szu.user.entity.Role;
import cn.edu.szu.user.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends CrudServiceImpl<Role> implements IRoleService {

    @Override
    public Role queryByName(String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rname", name);
        return getBaseMapper().selectOne(queryWrapper);
    }
}
