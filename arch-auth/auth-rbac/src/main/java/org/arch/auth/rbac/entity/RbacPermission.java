package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 权限表(rbac_permission)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_permission")
public class RbacPermission extends CrudEntity<RbacPermission> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
	private Long id;
    /**
     * 权限码query/creat/update/delete
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
     * url
     */
    private String permissionUri;
    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private String permissionTyp;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * isActive
     */
    private Integer isActive;
    /**
     * createdBy
     */
    private String createdBy;
    /**
     * createdDate
     */
    private Date createdDate;

}