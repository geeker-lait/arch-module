package org.arch.ums.account.vo;

import lombok.Data;

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

//    /**
//     * 账户ID
//     */
//    private String accountId;
//
    /**
     * 账户名
     */
    private String accountName;
//
//    /**
//     * 头像
//     */
//    private String icon;
//
//    /**
//     * 会员ID
//     */
//    private String memberId;
//
//    /**
//     * 商户ID
//     */
//    private String merchantId;
//
//    /**
//     * 密码
//     */
//    private String pwd;
//
//    /**
//     * 角色ID
//     */
//    private Long roleId;
//
//    /**
//     * 用户来源
//     */
//    private String source;
//    /**
//     * 用户ID
//     */
//    private String userId;

    private String token;
    private String appId;
    private String appCode;
}