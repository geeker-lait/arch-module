package org.arch.application.demo.crud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.arch.framework.api.crud.BaseSearchDto;

import java.util.Date;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/8/2020 9:10 PM
 */
@Data
@NoArgsConstructor
public class RbacCategorySearchDto extends BaseSearchDto {

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

    @Override
    protected void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_categoryName",this.getCategoryName(),map);
        putNoNull("EQ_isActive",this.getIsActive(),map);
    }
}
