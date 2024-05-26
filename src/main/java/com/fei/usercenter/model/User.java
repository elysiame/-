package com.fei.usercenter.model;

import lombok.Data;

/**
 * projectName:com.fei.usercenter.model
 *
 * @author 飞
 * @create by 2024/5/2615:04
 * description:
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}