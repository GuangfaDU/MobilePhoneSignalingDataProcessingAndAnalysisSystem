package cn.edu.szu.dao;

import cn.edu.szu.entity.BaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface ICrudDao<T extends BaseEntity> extends BaseMapper<T> {

}
