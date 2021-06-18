package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.ums.enums.ClientType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 授权客户端(AuthClient) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:12
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AuthClientSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
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
     * scope id 列表, 如: openid/userinfo/token/code/资源服务器标识等
     */
    private String scopes;

    /**
     * 角色 id 列表, 通过逗号分割
     */
    private String roleIds;

    /**
     * 客户端类型: web, 安卓, ios, 小程序…
     */
    private Integer clientType;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    @JsonIgnore
    private Integer appId;

    /**
     * 店铺 id
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @JsonIgnore
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_client_id", this.getClientId(), map);
        putNoNull("EQ_client_secret", this.getClientSecret(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_client_type", this.getClientType(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
        putNoNull("EQ_scopes", this.getScopes(), map);
        putNoNull("EQ_role_ids", this.getRoleIds(), map);
    }
}
