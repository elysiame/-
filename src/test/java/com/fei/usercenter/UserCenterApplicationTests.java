package com.fei.usercenter;

import com.fei.usercenter.mapper.UserMapper;
import com.fei.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class UserCenterApplicationTests {

    @Resource
    private UserMapper userMapper;
    @Test
    void testSelect(){
        System.out.println("----select all test-----");
        List<User> users = userMapper.selectList(null);
        Assertions.assertEquals(5,users.size());
        users.forEach(System.out::println);
    }

}
