package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

/**
 * 资源类目表(rbac_category)实体类
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_category")
public class RbacCategory extends CrudEntity<RbacCategory> {
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
     * 资源类目名
     */
    private String categoryName;
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