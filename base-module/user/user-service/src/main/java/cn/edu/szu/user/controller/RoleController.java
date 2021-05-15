package cn.edu.szu.user.controller;

import cn.edu.szu.controller.BaseController;
import cn.edu.szu.user.entity.Role;
import cn.edu.szu.user.service.IRoleService;
import org.springframework.web.bind.annotation.*;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<IRoleService, Role> {

    @GetMapping("/get/{name}")
    public Role queryByName(@PathVariable("name") String name) {
        return service.queryByName(name);
    }
}
