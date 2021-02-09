package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.automate.api.DirectiveRequest;

import java.time.LocalDateTime;

/**
 * 表单定义(FormDefinition) request
 *
 * @author lait
 * @date 2021-02-08 13:25:12
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormDefinitionRequest implements DirectiveRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单分类
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
    private Integer version;

    /**
     * 表单描述
     */
    private String descr;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}