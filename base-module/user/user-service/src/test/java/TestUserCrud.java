import cn.edu.szu.user.UserServiceApplication;
import cn.edu.szu.user.dao.UserDao;
import cn.edu.szu.user.entity.User;
import cn.edu.szu.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class TestUserCrud {

    @Autowired
    IUserService userService;

    @Autowired
    UserDao userDao;

    @Test
    public void testDynamicSql() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("email", "ch");
        IPage<User> page = new Page<>(1, 10);
        userDao.selectPage(page, queryWrapper).getRecords().forEach(System.out::println);
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setId(1L);
        user.setPassword("123456");
        boolean res = userService.saveOrUpdate(user);
        System.out.println(res);
    }

    @Test
    public void testUpdate() {
        User user = userService.getById(1L);
        user.setPassword("123456");
        System.out.println(userService.saveOrUpdate(user));;
    }

    @Test
    public void testSelectByName() {
        System.out.println(userService.queryByName("utvs_00002"));
    }
}
