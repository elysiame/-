package com.fei.usercenter.controller;

import com.fei.usercenter.model.domain.User;
import com.fei.usercenter.model.request.UserLoginRequest;
import com.fei.usercenter.model.request.UserRegisterRequest;
import com.fei.usercenter.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * projectName:com.fei.usercenter.controller
 *用户接口
 * @author 飞
 * @create by 2024/5/2617:54
 * description:
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(tags = "用户中心相关接口")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public  Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest==null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }

        Long id = userService.userRegister(userAccount, userPassword, checkPassword);
        return id;
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest,
                          HttpServletRequest request){
        if (userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }

        User user = userService.userLogin(userAccount, userPassword,request);

        return user;
    }
}
