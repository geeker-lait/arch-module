package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.ums.enums.ResourceType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-资源(Resource) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:29
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_resource")
public class Resource extends CrudEntity<Resource> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-资源表ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 账号-资源类目ID
     */
    @TableField(value = "`category_id`")
    private Long categoryId;

    /**
     * 资源名
     */
    @TableField(value = "`resource_name`")
    private String resourceName;

    /**
     * 资源码
     */
    @TableField(value = "`resource_code`")
    private String resourceCode;

    /**
     * 类型: 1目录, 2菜单, 3按钮, 4链接
     */
    @TableField(value = "`resource_type`")
    private ResourceType resourceType;

    /**
     * 资源值
     */
    @TableField(value = "`resource_val`")
    private String resourceVal;

    /**
     * 资源路径
     */
    @TableField(value = "`resource_path`")
    private String resourcePath;

    /**
     * 资源图标
     */
    @TableField(value = "`resource_icon`")
    private String resourceIcon;

    /**
     * 资源描述
     */
    @TableField(value = "`resource_desc`")
    private String resourceDesc;

    /**
     * 是否隐藏: 0不隐藏, 1隐藏. 默认: 0
     */
    @TableField(value = "`visible`")
    private Integer visible;

    /**
     * 层级
     */
    @TableField(value = "`level`")
    private Integer level;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 应用 id
     */
    @TableField(value = "`app_id`")
    private Integer appId;

    /**
     * 店铺 id
     */
    @TableField(value = "`store_id`")
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
    private Boolean deleted;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
