package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.ClientType;

import java.time.LocalDateTime;

/**
 * 授权客户端(AuthClient) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:12
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AuthClientRequest {

    /**
     * 授权客户端ID
     */
    private Long id;

    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 客户端 secret
     */
    private String clientSecret;

    /**
     * openid/userinfo/token/code/资源服务器标识等
     */
    private String scopes;

    /**
     * 客户端类型: web, 安卓, ios, 小程序…
     */
    private ClientType clientType;

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