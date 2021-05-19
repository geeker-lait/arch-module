package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.ResourceType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-资源(Resource) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:22:55
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ResourceRequest implements Serializable {
    private static final long serialVersionUID = 1L;

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

}