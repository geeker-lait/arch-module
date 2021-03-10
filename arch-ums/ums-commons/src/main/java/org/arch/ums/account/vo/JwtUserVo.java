package org.arch.ums.account.vo;

import lombok.Data;
import org.arch.framework.ums.enums.LoginType;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/28 16:19
 */
@Data
public class JwtUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号-标识(AccountIdentifier) ID, 一个标识 ID 可以对应多个 accountId
     */
    private Long unionId;

    /**
     * 账户名,邮箱,手机号,第三方用户ID
     */
    private String identifier;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录类型
     */
    private LoginType loginType;
    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户来源
     */
    private String source;
    /**
     * 用户ID
     */
    private String userId;

    private String token;
    private String appId;
    private String appCode;
}