package org.arch.auth.sso.request.bind;

import org.arch.framework.enums.ChannelType;

import javax.validation.constraints.NotNull;

/**
 * 用户名密码注册请求
 * @author YongWu zheng
 * @since 2021.1.3 15:53
 */
public class UsernameRegisterRequest {

    /**
     * 身份唯一标识
     */
    @NotNull
    private String username;
    /**
     * 凭证
     */
    @NotNull
    private String password;

    /**
     * 账号登录类型
     */
    private ChannelType channelType;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户来源
     */
    private String source;

}
