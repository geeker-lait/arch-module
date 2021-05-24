package org.arch.framework.ums.bean;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * 当前登录的账户信息
 * @author YongWu zheng
 * @since 2021.1.3 17:08
 */
@Data
@NoArgsConstructor
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = -1904982933624705568L;

    @Builder
    public TokenInfo(Long identifierId, Long accountId, Integer tenantId, String accountName, String nickName, String avatar,
                     Integer loginType, Collection<GrantedAuthority> authorities) {
        this.identifierId = identifierId;
        this.accountId = accountId;
        this.tenantId = tenantId;
        this.accountName = accountName;
        this.nickName = nickName;
        this.avatar = avatar;
        this.loginType = loginType;
        this.authorities = authorities;
    }

    /**
     * 账号标识(AccountIdentifier) id,
     */
    private Long identifierId;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long accountId;
    /**
     * 租户 ID
     */
    private Integer tenantId;
    /**
     * 对应与 AccountIdentifier 的 identifier 字段
     */
    private String accountName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 登录类型
     */
    private Integer loginType;

    private Collection<GrantedAuthority> authorities;

    /**
     * 获取 userId, 这里的 userId 即 accountId, 与 {@link #getAccountId()} 值相同
     * @return  返回 UserId, 这里的 userId 即 accountId
     */
    public Long getUserId() {
        return accountId;
    }

}

