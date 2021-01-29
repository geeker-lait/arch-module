package org.arch.ums.account.entity;

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
 * 账号-权限(Permission) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:17:05
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
    @TableId("id")
    private Long id;

    /**
     * 权限码(与RequestMethod对应)list(GET)/add(POST)/edit(PUT)/delete(DELETE)/..
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限值
     */
    private String permissionVal;

    /**
     * uri
     */
    private String permissionUri;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）, 4->链接
     */
    private ResourceType resourceType;

    /**
     * 排序
     */
    private Integer sorted;

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