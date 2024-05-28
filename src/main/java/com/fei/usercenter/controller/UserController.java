package com.fei.usercenter.controller;

import com.fei.usercenter.common.BaseResponse;
import com.fei.usercenter.common.ErrorCode;
import com.fei.usercenter.common.ResultUtils;
import com.fei.usercenter.exception.BusinessException;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)) {
            throw  new BusinessException(ErrorCode.NULL_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                          HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户或密码为空");
        }

        User user =  userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        List<User> userList = userService.searchUsers(username);
        return ResultUtils.success(userList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody Long id, HttpServletRequest request) {
        if (id <= 0) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (!isAdmin(request)) {
            throw  new BusinessException(ErrorCode.NO_AUTH);
        }
        Boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }


    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userLogout(request));
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
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
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return true;
    }
}
