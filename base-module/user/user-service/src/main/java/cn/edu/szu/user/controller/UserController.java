package cn.edu.szu.user.controller;

import cn.edu.szu.controller.BaseController;
import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.user.entity.User;
import cn.edu.szu.user.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController<IUserService, User> {

    @GetMapping("/get/{userName}")
    public User queryByName(@PathVariable("userName") String userName) {
        return service.queryByName(userName);
    }

    @PostMapping("/updateLogin")
    public void updateLoginTime(@RequestBody User entity) {
        entity.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        service.updateById(entity);
    }

    @GetMapping("/dynamic")
    public ResponseEntity dynamicQuery(@RequestParam(value = "username", required = false) String username,
                                       @RequestParam(value = "email", required = false) String email) {
        return new ResponseEntity()
                .setSuccess(true)
                .setMessage("查询成功")
                .setData(service.dynamicQuery(username, email));
    }
}
