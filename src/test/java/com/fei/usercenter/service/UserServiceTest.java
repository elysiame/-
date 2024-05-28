package com.fei.usercenter.service;
import java.util.Date;

import com.fei.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    UserService userService;

    @Test
    void testSelect(){
        User user = new User();
        user.setUsername("张三");
        user.setUserAccount("zhangsan");
        user.setAvatarUrl("https://images.zsxq.com/FnGzXG5f9VO5YEQpHXbxEh9RSqh8?e=1719763199&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:cPbtGVqR2ESuWrwWCYbEl_TiOYQ=");
        user.setGender(0);
        user.setUserPassword(DigestUtils.md5DigestAsHex(("fei"+"12345678").getBytes()));
        user.setPhone("123456");
        user.setEmail("fsrgd");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        boolean b = userService.save(user);
        System.out.println(user.getId());
        assertTrue(b);
    }

    @Test
    void userRegister() {
        String userAccount = "lisi";
        String password = "123456";
        String checkPassword = "123456";
        String planetCode = "004";
        long l = userService.userRegister(userAccount, password, checkPassword,planetCode);
        System.out.println(l);
        Assertions.assertEquals(-1,l);
    }

    @Test
    void testDelete(){
        Long id = 1l;
        boolean b = userService.removeById(id);
        System.out.println(b);
    }
}