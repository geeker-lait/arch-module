package org.arch.auth.sso.properties;

import lombok.Getter;
import lombok.Setter;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.enums.SourceType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;

/**
 * sso 属性
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:15
 */
@ConfigurationProperties(prefix = "arch.sso")
@Getter
@Setter
@Validated
public class SsoProperties {

    /**
     * 手机用户默认密码
     */
    private String defaultPassword = "VR&vU'a#.mt^&%'";

    /**
     * 默认用户头像
     */
    @NotNull(message = "arch.defaultAvatar 属性必须设置默认用户头像")
    private String defaultAvatar;

    /**
     * 用户注册成功后的默认权限, 多个权限用逗号分开, 默认为: "ROLE_USER"
     */
    private String defaultAuthorities = "ROLE_USER";

    // =========== 第三方登录相关 ============

    /**
     * 第三方登录成功后, 返回获取 token 自动脚本页面. 默认: /oauth2Token
     */
    private String autoGetTokenUri = "/oauth2Token";

    /**
     * 第三方登录成功后, autoGetTokenUrl 获取 token 处理接口 url, 默认: /oauth2Callback
     */
    private String oauth2CallbackUri = "/oauth2Callback";

    /**
     * 第三方登录成功后, oauth2CallbackUri 接收的参数名称, 默认: tk
     */
    private String oauth2TokenParamName = "tk";

    /**
     * 存储 OAuth2Token 的临时缓存前缀: 默认: TEMP:OAuth2Token:
     */
    private String tempOauth2TokenPrefix = "TEMP:OAuth2Token:";
    /**
     * token 与 refreshToken 的分隔符: 默认: #@#:
     */
    private String delimiterOfTokenAndRefreshToken = "#@#";

    /**
     * 第三方登录成功后, 临时存储在 redis 的 token 值的 TTL, 默认: 30 秒
     */
    private Duration tempOauth2TokenTimeout = Duration.ofSeconds(30);

    /**
     * {@link SourceType} 参数名称, 默认: _from
     */
    private String sourceParameterName = "_from";

    /**
     * {@link AccountType} 参数名称, 默认: accountType
     */
    private String accountTypeParameterName = "accountType";
}
