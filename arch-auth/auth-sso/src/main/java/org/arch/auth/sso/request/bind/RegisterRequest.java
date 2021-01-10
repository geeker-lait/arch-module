package org.arch.auth.sso.request.bind;

import lombok.Data;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.enums.ChannelType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户名密码注册请求
 * @author YongWu zheng
 * @since 2021.1.3 15:53
 */
@Data
public class RegisterRequest {

    /**
     * 身份唯一标识, 对应: {@code AccountIdentifier.identifier}
     */
    @NotNull(message = "用户名不能空")
    private String username;
    /**
     * 凭证, 对应: {@code AccountIdentifier.credential}
     */
    @Length(min = 8, message = "密码长度至少八位")
    private String password;

    /**
     * 账号登录类型
     */
    private ChannelType channelType;

    /**
     * 账号类型
     */
    @NotNull(message = "账号类型不能为空")
    private AccountType accountType;

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
