package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单布局(FormLayout) search dto
 *
 * @author lait
 * @date 2021-02-08 13:25:28
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormLayoutSearchDto extends BaseSearchDto {

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_table_id", this.getTableId(), map);
        putNoNull("EQ_layout_name", this.getLayoutName(), map);
        putNoNull("EQ_layout_code", this.getLayoutCode(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_editor_source_id", this.getEditorSourceId(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
