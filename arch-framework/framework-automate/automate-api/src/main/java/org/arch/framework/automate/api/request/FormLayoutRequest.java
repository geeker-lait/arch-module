package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单布局(FormLayout) request
 *
 * @author lait
 * @date 2021-02-08 13:25:28
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormLayoutRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 表ID
     */
    private Long tableId;

    /**
     * 布局名称
     */
    private String layoutName;

    /**
     * 布局码
     */
    private String layoutCode;

    /**
     * 描述
     */
    private String descr;

    /**
     * 原sourceId
     */
    private Long editorSourceId;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}
