package com.uni.skit.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description: 用户注册请求信息
 *
 * @author kenzhao
 * @date 2020/3/28 16:27
 */
@Data
public class AuthRegDto {

    private Long id;
    /**
     * 应用CODE
     */
    private String appCode;

    /**
     * 应用ID
     */
    @NotNull
    private String appId;

    /**
     * 账户名,手机号
     */
    @NotNull
    private String accountName;

    /**
     * 头像
     */
    private String icon;


    /**
     * 密码
     */
    private String pwd;

    /**
     * 用户来源
     */
    private String source;
    /**
     * 注册验证码
     */
    private String verifyCode;
}