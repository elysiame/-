package com.fei.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fei.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   账户
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return long
     */

    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 返回用户信息（脱敏）
     *
     * @param userAccount  账户
     * @param userPassword 密码
     * @param request      得到一个session
     * @return user
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户类
     */
    List<User> searchUsers(String username);

    /**
     * 用户注销
     *
     * @param request 请求
     */

    Integer userLogout(HttpServletRequest request);

    User getSafetyUser(User user);
}
