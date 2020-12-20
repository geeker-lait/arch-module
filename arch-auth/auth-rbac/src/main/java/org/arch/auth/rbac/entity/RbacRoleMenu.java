package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 角色菜单表(rbac_role_menu)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_role_menu")
public class RbacRoleMenu extends CrudEntity<RbacRoleMenu> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
	private Long id;
    /**
     * 角色_i_d
     */
    private Long roleId;
    /**
     * 菜单_i_d
     */
    private Long menuId;
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