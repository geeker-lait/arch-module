package org.arch.auth.sso.request.bind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.arch.framework.ums.enums.LoginType;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册请求信息
 *
 * @author YongWu zheng
 * @since 2020.12.28 11:39
 */
@Data
@NoArgsConstructor
public class RegRequest implements Serializable {

    private static final long serialVersionUID = -7953228309397753374L;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long aid;

    /**
     * 租户 ID
     */
    private Integer tenantId;

    /**
     * 账户名,邮箱,手机号,第三方用户ID, 对应账号-标识 identifier
     */
    @Pattern(regexp = "[A-Za-z0-9]{3,32}", message = "用户名称长度必须大于 3 位, 小于 32 位, 且只能是A-Za-z0-9字符")
    private String username;
    /**
     * 站内账号是密码, 第三方是 accessToken, 手机登录是空字符串或指定字符串. 对应账号-标识 credential<br>
     * 注意: {@link #loginType} 为 {@link LoginType#PHONE} 时有后端赋值 "空字符串或指定字符串"
     */
    @Pattern(regexp = "\\w{8,32}", message = "密码长度必须大于等于 8 位")
    private String password;

    /**
     * 用户权限.
     */
    private String authorities;

    /**
     * 登录类型: 用户名密码(USERNAME), 邮箱(EMAIL), 手机(PHONE), 第三方(OAUTH2),
     * 由后端赋值
     */
    private Integer loginType;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户昵称, 第三方登录用第三方的用户名, 手机号登录与用户密码登录随机生成
     */
    private String nickName;

    /**
     * 用户来源(/百度/抖音/头条/微信/微信公众号/微信小程序/用户推荐(user_userId)), 可以为 null.
     */
    private String source;

}