package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单字段类型(FormFieldTyp) request
 *
 * @author lait
 * @date 2021-02-08 13:25:22
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldTypRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 类型code：input/checkbox/radio/select/textarea
     */
    private String typCode;

    /**
     * 类型名称
     */
    private String typName;

    /**
     * 描述
     */
    private String descr;

    /**
     * 排序
     */
    private Integer sorted;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}