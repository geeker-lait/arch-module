package org.arch.framework.automate.from.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单定义(FormDefinition) request
 *
 * @author lait
 * @date 2021-02-10 15:44:44
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormDefinitionRequest {

    /**
     * 表单分类
     */
    private Long id;

    /**
     * schema主键id
     */
    private String category;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单code,用作页面表单
     */
    private String formCode;

    /**
     * 关联数据表
     */
    private String tableName;

    /**
     * 表单部署字段ID
     */
    private Long fieldId;

    /**
     * 表单部署布局ID
     */
    private Long layoutId;

    /**
     * 版本
     */
    private Integer ver;

    /**
     * 表单描述
     */
    private String descr;

    /**
     * 定义json
     */
    private String definitionJson;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}
