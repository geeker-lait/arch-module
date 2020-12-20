package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 角色表(rbac_role)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_role")
public class RbacRole extends CrudEntity<RbacRole> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
	private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色码
     */
    private String roleCode;
    /**
     * 角色图标
     */
    private String icon;
    /**
     * 角色描述
     */
    private String descr;
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