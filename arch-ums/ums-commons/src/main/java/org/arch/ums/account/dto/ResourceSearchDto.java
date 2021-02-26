package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.ums.enums.ResourceType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-资源(Resource) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:22:56
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ResourceSearchDto implements BaseSearchDto {

    /**
     * 账号-资源表ID
     */
    private Long id;

    /**
     * 账号-资源类目ID
     */
    private Long categoryId;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 资源码
     */
    private String resourceCode;

    /**
     * 类型: 1目录, 2菜单, 3按钮, 4链接
     */
    private ResourceType resourceType;

    /**
     * 资源值
     */
    private String resourceVal;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 资源图标
     */
    private String resourceIcon;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 是否隐藏: 0不隐藏, 1隐藏. 默认: 0
     */
    private Integer visible;

    /**
     * 层级
     */
    private Integer level;

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_category_id", this.getCategoryId(), map);
        putNoNull("EQ_level", this.getLevel(), map);
        putNoNull("EQ_visible", this.getVisible(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_resource_name", this.getResourceName(), map);
        putNoNull("EQ_resource_code", this.getResourceCode(), map);
        putNoNull("EQ_resource_type", this.getResourceType(), map);
        putNoNull("EQ_resource_val", this.getResourceVal(), map);
        putNoNull("EQ_resource_path", this.getResourcePath(), map);
        putNoNull("EQ_resource_icon", this.getResourceIcon(), map);
        putNoNull("EQ_resource_desc", this.getResourceDesc(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
