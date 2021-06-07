package cn.edu.szu.controller;

import cn.edu.szu.entity.BaseEntity;
import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.service.ICrudService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GuangfaDu
 * @version 1.0
 */
public abstract class BaseController<S extends ICrudService<T>, T extends BaseEntity> {

    @Autowired
    protected S service;

    @GetMapping("/list")
    public IPage<T> listByPage(T entity,
                               @RequestParam(name = "pageNum", defaultValue = "1", required = false) Long pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "10", required = false) Long pageSize) {
        return service.listByPage(entity, pageNum, pageSize);
    }

    @GetMapping("/edit/{id}")
    public T edit(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody T entity) {
        ResponseEntity responseEntity = new ResponseEntity();
        boolean res = service.saveOrUpdate(entity);
        if (res) {
            responseEntity.setSuccess(true);
            responseEntity.setMessage("保存成功");
            return responseEntity;
        }

        responseEntity.setSuccess(false);
        responseEntity.setMessage("保存失败");
        responseEntity.setData(entity);
        return  responseEntity;
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        ResponseEntity responseEntity = new ResponseEntity();
        boolean res = service.removeById(id);
        if (res) {
            responseEntity.setSuccess(true);
            responseEntity.setMessage("删除成功");
            return responseEntity;
        }

        responseEntity.setSuccess(false);
        responseEntity.setMessage("删除失败");
        responseEntity.setData(id);
        return responseEntity;
    }
}
