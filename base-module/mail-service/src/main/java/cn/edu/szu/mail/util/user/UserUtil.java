package cn.edu.szu.mail.util.user;

import cn.edu.szu.mail.client.UserClient;
import cn.edu.szu.mail.entity.RequestMail;
import cn.edu.szu.user.entity.Role;
import cn.edu.szu.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class UserUtil {

    @Autowired
    private UserClient userClient;
    @Autowired
    private ObjectMapper objectMapper;

    public boolean saveUser(String username, String password, RequestMail requestMail) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        user.setEmail(requestMail.getEmail());
        try {
            String roleJson = userClient.queryByName(requestMail.getRole());
            user.setRoleId(objectMapper.readValue(roleJson, Role.class).getId());
            userClient.save(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
