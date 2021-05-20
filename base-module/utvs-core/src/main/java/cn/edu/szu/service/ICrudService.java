package cn.edu.szu.service;

import cn.edu.szu.entity.BaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface ICrudService<T extends BaseEntity> extends IService<T> {

    /**
     * 对应DAO的动态SQL分页查询业务
     * @param entity 条件查询对象
     * @param pageNum 分页查询的当前页数
     * @param pageSize 分页查询的单页数目
     * @return 分页查询的结果
     *
     * @see IPage
     */
    IPage<T> listByPage(T entity, Long pageNum, Long pageSize);
}
