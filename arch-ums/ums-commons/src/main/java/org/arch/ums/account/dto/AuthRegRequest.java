package org.arch.ums.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.arch.framework.ums.enums.LoginType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册请求信息
 * @author YongWu zheng
 * @since 2020.12.28 11:39
 */
@Data
@AllArgsConstructor
@Builder
public class AuthRegRequest implements Serializable {

    private static final long serialVersionUID = -7953228309397753374L;

    public AuthRegRequest() {
    }

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    @NotNull(message = "ID 不能为空")
    private Long aid;

    /**
     * 租户 ID
     */
    @NotNull(message = "租户 ID 不能为空")
    private Integer tenantId;

    /**
     * 账户名,邮箱,手机号,第三方用户ID, 对应账号-标识 identifier
     */
    @NotNull(message = "账户名不能为空")
    private String identifier;
    /**
     * 站内账号是密码, 第三方是 accessToken, 手机登录是空字符串或指定字符串. 对应账号-标识 credential<br>
     * 注意: 为 {@link LoginType#PHONE} 时由后端赋值 "空字符串或指定字符串"
     */
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9*/+.~!@#$%^&()]{8,16}$", message =
            "至少包含数字跟大小写字母，可以有*/+.~!@#$%^&()字符, 密码长度必须在 8-16 位之间")
    private String credential;
    /**
     * 用户权限.
     */
    private String authorities;

    /**
     * 登录类型: 站内用户(ACCOUNT), 邮箱(EMAIL), 手机(PHONE), 第三方(OAUTH2),
     * 由后端赋值
     */
    @NotNull(message = "登录类型不能为空")
    private LoginType loginType;
    /**
     * 头像
     */
    @NotNull(message = "用户头像不能为空")
    private String avatar;

    /**
     * 用户昵称, 第三方登录用第三方的用户名, 手机号登录与用户密码登录随机生成
     */
    @NotNull(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户来源(/百度/抖音/头条/微信/微信公众号/微信小程序/用户推荐(user_userId)), 可以为 null.
     */
    private String source;

}