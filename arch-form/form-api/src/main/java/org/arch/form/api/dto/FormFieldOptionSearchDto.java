package org.arch.form.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单字段选项(FormFieldOption) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:45
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldOptionSearchDto implements BaseSearchDto {

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_field_id", this.getFieldId(), map);
        putNoNull("EQ_option_name", this.getOptionName(), map);
        putNoNull("EQ_option_code", this.getOptionCode(), map);
        putNoNull("EQ_option_val", this.getOptionVal(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}