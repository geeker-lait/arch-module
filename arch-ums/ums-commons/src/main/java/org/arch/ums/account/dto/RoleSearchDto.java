package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-角色(Role) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:23:45
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RoleSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * 账号角色ID
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色icon
     */
    private String icon;

    /**
     * 顺序
     */
    private Integer sorted;

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
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_role_name", this.getRoleName(), map);
        putNoNull("EQ_icon", this.getIcon(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
