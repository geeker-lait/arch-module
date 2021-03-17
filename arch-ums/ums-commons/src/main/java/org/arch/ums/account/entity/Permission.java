package org.arch.ums.account.entity;

import java.time.LocalDateTime;

import org.arch.framework.ums.enums.ResourceType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;

/**
 * 账号-权限(Permission) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:28
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_permission")
public class Permission extends CrudEntity<Permission> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-菜单ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 权限码(与RequestMethod对应)list(GET)/add(POST)/edit(PUT)/delete(DELETE)/..
     */
    @TableField(value = "`permission_code`")
    private String permissionCode;

    /**
     * 权限名称
     */
    @TableField(value = "`permission_name`")
    private String permissionName;

    /**
     * 权限值
     */
    @TableField(value = "`permission_val`")
    private String permissionVal;

    /**
     * uri
     */
    @TableField(value = "`permission_uri`")
    private String permissionUri;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）, 4->链接
     */
    @TableField(value = "`resource_type`")
    private ResourceType resourceType;

    /**
     * 排序
     */
    @TableField(value = "`sorted`")
    private Integer sorted;

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
