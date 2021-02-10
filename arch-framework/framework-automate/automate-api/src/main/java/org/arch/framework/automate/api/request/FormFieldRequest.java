package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单字段(FormField) request
 *
 * @author lait
 * @date 2021-02-10 15:55:44
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldRequest {

    /**
     * 表单Id
     */
    private Long id;

    /**
     * 字段Id
     */
    private Long formId;

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
    private LocalDateTime dt;

}
