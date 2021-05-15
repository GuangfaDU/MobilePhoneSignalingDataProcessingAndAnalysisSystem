package cn.edu.szu.controller;

import cn.edu.szu.entity.BaseEntity;
import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.service.ICrudService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GuangfaDu
 * @version 1.0
 */
public abstract class BaseController<S extends ICrudService<T>, T extends BaseEntity> {

    @Autowired
    protected S service;

    @ApiOperation(value = "分页查询", notes = "依据API接口描述提供参数，所有参数都是可选的")
    @GetMapping("/list")
    public IPage<T> listByPage(T entity,
                               @RequestParam(name = "pageNum", defaultValue = "1", required = false) Long pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "10", required = false) Long pageSize) {
        return service.listByPage(entity, pageNum, pageSize);
    }

    @ApiOperation(value = "加载信息", notes = "根据Id加载待修改的信息")
    @GetMapping("/edit/{id}")
    public T edit(@PathVariable Long id) {
        return service.getById(id);
    }

    @ApiOperation(value = "保存信息", notes = "Id存在则判断为修改数据操作，否则为添加数据操作")
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

    @ApiOperation(value = "删除", notes = "根据Id进行删除")
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
