package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 资源表(rbac_resource)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_resource")
public class RbacResource extends CrudEntity<RbacResource> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
	private Long id;
    /**
     * 类目id
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
     * 类型:1目录、2菜单、3按钮、4链接
     */
    private String resourceTyp;
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
    private String resourceDescr;
    /**
     * 是否隐藏
     */
    private Integer visible;
    /**
     * 层级
     */
    private Integer level;
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