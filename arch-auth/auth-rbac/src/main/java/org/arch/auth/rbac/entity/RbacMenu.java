package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 权限表(rbac_menu)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_menu")
public class RbacMenu extends CrudEntity<RbacMenu>  {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
	private Long id;
    /**
     * 父节点_i_d
     */
    private Long pid;
    /**
     * 英文码
     */
    private String menuCode;
    /**
     * 名称
     */
    private String menuName;
    /**
     * 值
     */
    private String menuVal;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 是否iframe
     */
    private Integer isFrame;
    /**
     * 图标
     */
    private String icon;
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