package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单字段(FormField) search dto
 *
 * @author lait
 * @date 2021-02-08 13:25:15
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldSearchDto implements BaseSearchDto {

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_form_id", this.getFormId(), map);
        putNoNull("EQ_field_id", this.getFieldId(), map);
        putNoNull("EQ_field_code", this.getFieldCode(), map);
        putNoNull("EQ_field_typ", this.getFieldTyp(), map);
        putNoNull("EQ_field_name", this.getFieldName(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
