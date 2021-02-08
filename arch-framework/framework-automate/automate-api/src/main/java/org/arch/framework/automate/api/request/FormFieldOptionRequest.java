package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单字段选项(FormFieldOption) request
 *
 * @author lait
 * @date 2021-02-08 13:25:19
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldOptionRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 字段id
     */
    private Long fieldId;

    /**
     * 选项id
     */
    private Long optionId;

    /**
     * 选项名
     */
    private String optionName;

    /**
     * 选项值
     */
    private String optionVal;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}
