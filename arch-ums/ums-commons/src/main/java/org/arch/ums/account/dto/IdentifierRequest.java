package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.ChannelType;

import java.time.LocalDateTime;

/**
 * 用户-标识(Identifier) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:04:39
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class IdentifierRequest {

    /**
     * 账号-标识 ID
     */
    private Long id;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long aid;

    /**
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；
     */
    private String identifier;

    /**
     * 授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；
     */
    private String credential;

    /**
     * 用户角色:ROLE_xxx 与 租户id: TENANT_XXX
     */
    private String authorities;

    /**
     * 登录类型【IDENTITYTYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private ChannelType channelType;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    private Integer appId;

    /**
     * 店铺 id
     */
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime st;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

}