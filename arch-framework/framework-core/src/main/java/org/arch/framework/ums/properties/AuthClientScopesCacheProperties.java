package org.arch.framework.ums.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.31 14:21
 */
@ConfigurationProperties(prefix = "arch.cron.scopes")
@Getter
@Setter
public class AuthClientScopesCacheProperties {

    /**
     * 是否需要更新 {@code AuthClient#getScopes()} 的本地缓存的 cron 表达式, 默认: 0 0/10 * * * ?
     */
    private String cronExp = "0 0/10 * * * ?";

    /**
     * 当此 redis hash key 中 field "ALL" 有值时表示需要更新本地缓存. <br>
     * 换句话说, 每次更新 {@code AuthClient} 时都需要:<br>
     * 1. del "AUTH:CLIENT:SCOPES:UPDATED" <br>
     * 2. hSet "AUTH:CLIENT:SCOPES:UPDATED" "ALL" 1.<br>
     * 当更新本地缓存后: <br>
     * hSet "AUTH:CLIENT:SCOPES:UPDATED" mac地址 1 来表示已经更新.
     */
    private String scopesCacheUpdatedRedisHashKey = "AUTH:CLIENT:SCOPES:UPDATED";
    /**
     * 当此 redis hash key 中 field "ALL" 有值时表示需要更新本地缓存. <br>
     * 换句话说, 每次更新 {@code AuthClient} 时都需要:<br>
     * 1. del "AUTH:CLIENT:SCOPES:UPDATED" <br>
     * 2. hSet "AUTH:CLIENT:SCOPES:UPDATED" "ALL" 1.<br>
     * 当更新本地缓存后: <br>
     * hSet "AUTH:CLIENT:SCOPES:UPDATED" mac地址 1 来表示已经更新.
     */
    private String scopesCacheUpdatedRedisHashField = "ALL";
}
