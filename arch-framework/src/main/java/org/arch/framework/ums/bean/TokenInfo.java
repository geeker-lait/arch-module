package org.arch.framework.ums.bean;

import lombok.Builder;
import lombok.Data;
import org.arch.framework.ums.enums.ChannelType;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * 当前登录的账户信息
 * @author YongWu zheng
 * @since 2021.1.3 17:08
 */
@Data
@Builder
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = -1904982933624705568L;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long accountId;
    /**
     * 对应与 AccountIdentifier 的 identifier 字段
     */
    private String accountName;
    /**
     * 登录类型
     */
    private ChannelType channelType;

    private Collection<GrantedAuthority> authorities;

    /**
     * 获取 userId, 这里的 userId 即 accountId, 与 {@link #getAccountId()} 值相同
     * @return  返回 UserId, 这里的 userId 即 accountId
     */
    public Long getUserId() {
        return accountId;
    }

}
