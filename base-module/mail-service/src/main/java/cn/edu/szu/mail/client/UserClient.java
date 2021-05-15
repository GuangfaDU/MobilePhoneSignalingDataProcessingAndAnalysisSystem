package cn.edu.szu.mail.client;

import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.user.entity.Role;
import cn.edu.szu.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@FeignClient("user-service")
public interface UserClient {

    /**
     * 保存指定用户到数据库
     * @param entity 用户实例
     * @return 包含数据的消息响应体
     */
    @PostMapping("/user/save")
    ResponseEntity save(@RequestBody User entity);

    /**
     * 根据角色名称查询角色
     * @param name 角色名称
     * @return 角色信息
     */
    @GetMapping("/role/get/{name}")
    String queryByName(@PathVariable("name") String name);
}
