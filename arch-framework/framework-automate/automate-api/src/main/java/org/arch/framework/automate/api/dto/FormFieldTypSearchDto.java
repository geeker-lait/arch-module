package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单字段类型(FormFieldTyp) search dto
 *
 * @author lait
 * @date 2021-02-08 13:25:22
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormFieldTypSearchDto extends BaseSearchDto {

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_typ_code", this.getTypCode(), map);
        putNoNull("EQ_typ_name", this.getTypName(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
