package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单字段(FormField) request
 *
 * @author lait
 * @date 2021-02-08 13:25:15
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单Id
     */
    private Long formId;

    /**
     * 字段Id
     */
    private Long fieldId;

    /**
     * 字段编码，自动生成，对应到数据库中的字段名
     */
    private String fieldCode;

    /**
     * 字段类型，对应到field_type表中的type_code
     */
    private String fieldTyp;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String descr;

    /**
     * 字段排序
     */
    private Integer sorted;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}
