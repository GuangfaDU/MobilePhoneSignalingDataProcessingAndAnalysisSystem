package cn.edu.szu.gateway.client;

import cn.edu.szu.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@FeignClient(name = "user-service")
@RequestMapping("/user")
public interface UserClient {

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 用户实例
     */
    @GetMapping("/get/{userName}")
    User queryByName(@PathVariable("userName") String userName);

    /**
     * 更新指定用户的登陆时间
     * @param user 指定的用户
     */
    @PostMapping(value = "/updateLogin", consumes = "application/json")
    void updateLogin(@RequestBody User user);
}
