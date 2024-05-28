package com.fei.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * projectName:com.fei.usercenter.common
 *通用返回类
 * @author 飞
 * @create by 2024/5/2816:21
 * description:
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 3991030040785175994L;

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data,String message) {
        this(code,data,null,null);
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
