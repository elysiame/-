package com.fei.usercenter.controller;

import com.fei.usercenter.model.domain.User;
import com.fei.usercenter.model.request.UserLoginRequest;
import com.fei.usercenter.model.request.UserRegisterRequest;
import com.fei.usercenter.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.fei.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.fei.usercenter.constant.UserConstant.UER_LOGIN_STATE;

/**
 * projectName:com.fei.usercenter.controller
 * 用户接口
 *
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
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest,
                          HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }

        return userService.searchUsers(username);
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody Long id, HttpServletRequest request) {
        if (id <= 0||!isAdmin(request)) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 权限校验
     * @param request 获取session
     * @return boolean
     */
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(UER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || !user.getUserRole().equals(ADMIN_ROLE)) {
            return false;
        }
        return true;
    }
}
