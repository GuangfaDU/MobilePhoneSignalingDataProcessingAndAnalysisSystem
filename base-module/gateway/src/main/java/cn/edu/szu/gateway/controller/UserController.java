package cn.edu.szu.gateway.controller;

import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.gateway.client.UserClient;
import cn.edu.szu.gateway.util.BCrypt;
import cn.edu.szu.gateway.util.JwtUtil;
import cn.edu.szu.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/security/login")
    public ResponseEntity login(User user) throws JsonProcessingException {
        String userName = user.getUsername();
        String password = user.getPassword();
        User expectedUser = userClient.queryByName(userName);
        if (expectedUser == null) {
            HashMap<String, String> retUser = new HashMap<>(2);
            retUser.put("username", userName);
            retUser.put("password", password);
            return new ResponseEntity().setSuccess(false).setMessage("用户名错误或不存在").setData(retUser);
        }

        if ("unavailable".equals(expectedUser.getStatus())) {
            return new ResponseEntity().setSuccess(false).setMessage("账号已被锁定，请联系系统管理员").setData(user);
        }

        if (!BCrypt.checkpw(password, expectedUser.getPassword())) {
            HashMap<String, String> retUser = new HashMap<>(2);
            retUser.put("username", userName);
            retUser.put("password", password);
            return new ResponseEntity().setSuccess(false).setMessage("密码错误").setData(retUser);
        }

        userClient.updateLogin(expectedUser);

        Map<String, Object> info = new HashMap<>(4);
        info.put("role", "USER");
        info.put("success", "SUCCESS");
        info.put("username", userName);
        String jwt = JwtUtil.createJwt(UUID.randomUUID().toString(), objectMapper.writeValueAsString(info), null);
        HashMap<String, Object> res = new HashMap<>(2);
        res.put("jwt", jwt);
        res.put("user", expectedUser);
        return new ResponseEntity().setSuccess(true).setMessage("登陆成功").setData(res);
    }
}
