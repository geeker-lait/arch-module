package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单实例(FormInstance) search dto
 *
 * @author lait
 * @date 2021-02-08 13:25:25
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormInstanceSearchDto extends BaseSearchDto {

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_form_id", this.getFormId(), map);
        putNoNull("EQ_form_name", this.getFormName(), map);
        putNoNull("EQ_form_code", this.getFormCode(), map);
        putNoNull("EQ_form_table_id", this.getFormTableId(), map);
        putNoNull("EQ_form_table_name", this.getFormTableName(), map);
        putNoNull("EQ_form_descr", this.getFormDescr(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
