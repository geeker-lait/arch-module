package org.arch.form.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表单字段选项(FormFieldOption) request
 *
 * @author lait
 * @date 2021-02-10 15:55:45
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldOptionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段id
     */
    private Long id;

    /**
     * 表单Id
     */
    private Long fieldId;

    /**
     * 选项id
     */
    private String optionName;

    /**
     * 选项名
     */
    private String optionCode;

    /**
     * 选项值
     */
    private String optionVal;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}