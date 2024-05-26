package com.fei.usercenter.service;
import java.util.Date;

import com.fei.usercenter.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    UserService userService;

    @Test
    void testSelect(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setUserAccount("12345");
        user.setAvatarUrl("https://images.zsxq.com/FnGzXG5f9VO5YEQpHXbxEh9RSqh8?e=1719763199&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:cPbtGVqR2ESuWrwWCYbEl_TiOYQ=");
        user.setGender(0);
        user.setUserPassword("xxxx");
        user.setPhone("123456");
        user.setEmail("fsrgd");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        boolean b = userService.save(user);
        System.out.println(user.getId());
        assertTrue(b);
    }
}