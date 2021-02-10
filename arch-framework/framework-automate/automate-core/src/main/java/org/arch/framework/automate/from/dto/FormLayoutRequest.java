package org.arch.framework.automate.from.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单布局(FormLayout) request
 *
 * @author lait
 * @date 2021-02-10 15:45:11
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
     * 布局css样式
     */
    private String layoutStyle;

    /**
     * 描述
     */
    private String descr;

    /**
     * 版本
     */
    private Integer ver;

    /**
     * 是否逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}
