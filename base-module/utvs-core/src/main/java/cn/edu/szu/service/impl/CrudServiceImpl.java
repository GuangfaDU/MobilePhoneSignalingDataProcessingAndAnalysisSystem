package cn.edu.szu.service.impl;

import cn.edu.szu.dao.ICrudDao;
import cn.edu.szu.entity.BaseEntity;
import cn.edu.szu.service.ICrudService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class CrudServiceImpl<T extends BaseEntity> extends ServiceImpl<ICrudDao<T>, T> implements ICrudService<T> {

    @Override
    public IPage<T> listByPage(T entity, Long pageNum, Long pageSize) {
        Page<T> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPage(page, Wrappers.emptyWrapper());
    }
}
