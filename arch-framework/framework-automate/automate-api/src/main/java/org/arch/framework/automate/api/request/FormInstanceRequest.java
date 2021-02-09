package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单实例(FormInstance) request
 *
 * @author lait
 * @date 2021-02-08 13:25:25
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormInstanceRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单code,用作页面表单
     */
    private String formCode;

    /**
     * 表单对应的表ID
     */
    private Long formTableId;

    /**
     * 表单对应数据库中生成的table name
     */
    private String formTableName;

    /**
     * 表单描述
     */
    private String formDescr;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}
