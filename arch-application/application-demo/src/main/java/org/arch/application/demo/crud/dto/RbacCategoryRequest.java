package org.arch.application.demo.crud.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 1/21/2021 5:58 PM
 */
@Data
public class RbacCategoryRequest {

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
