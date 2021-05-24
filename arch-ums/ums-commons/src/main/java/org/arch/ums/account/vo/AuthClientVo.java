package org.arch.ums.account.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 客户端 scope 认证 vo
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.31 13:36
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AuthClientVo {
    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 客户端 secret
     */
    private String clientSecret;

    /**
     * scope id 列表, 如: openid/userinfo/token/code/资源服务器标识等
     */
    private String scopes;

    /**
     * 角色 id 列表, 通过逗号分割
     */
    private String roleIds;

    /**
     * 租户 id
     */
    private Integer tenantId;

}
