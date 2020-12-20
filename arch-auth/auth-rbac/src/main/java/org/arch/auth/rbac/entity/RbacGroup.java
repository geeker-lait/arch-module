package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 组织机构表(rbac_group)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_group")
public class RbacGroup extends CrudEntity<RbacGroup> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
	private Long id;
    /**
     * 父id
     */
    private Long groupPid;
    /**
     * 组code
     */
    private String groupCode;
    /**
     * 组织架构名
     */
    private String groupName;
    /**
     * 组织架构_i_c_o_n
     */
    private String groupIcon;
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