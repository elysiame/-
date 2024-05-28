package com.fei.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * projectName:com.fei.usercenter.model.request
 *
 * @author 飞
 * @create by 2024/5/2715:10
 * description:用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 5787421263060414232L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
