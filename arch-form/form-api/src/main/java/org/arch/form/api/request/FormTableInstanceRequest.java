package org.arch.form.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表单实例(FormTableInstance) request
 *
 * @author lait
 * @date 2021-02-10 15:55:52
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormTableInstanceRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * schema主键id
     */
    private Long id;

    /**
     * 表单ID
     */
    private Long projectId;

    /**
     * 表单名称
     */
    private Long formDefinitionId;

    /**
     * 表单code,用作页面表单
     */
    private Long interfaceIds;

    /**
     * 表单对应的表ID
     */
    private String formName;

    /**
     * 表单对应数据库中生成的table name
     */
    private String formCode;

    /**
     * 表单描述
     */
    private Long formTableId;

    /**
     * 表单名称
     */
    private String formTableName;

    /**
     * 描述
     */
    private String descr;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}
