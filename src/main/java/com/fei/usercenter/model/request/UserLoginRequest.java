package com.fei.usercenter.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * projectName:com.fei.usercenter.model.request
 *
 * @author 飞
 * @create by 2024/5/2715:10
 * description:用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 4731522895557129402L;

    private String userAccount;

    private String userPassword;

}
