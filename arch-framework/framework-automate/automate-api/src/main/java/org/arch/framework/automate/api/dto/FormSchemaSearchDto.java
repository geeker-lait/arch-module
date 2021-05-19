package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单schema(FormSchema) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormSchemaSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 库名称/项目名称
     */
    private String schemaName;

    /**
     * 库名称/项目名称的code
     */
    private String schemaCode;

    /**
     * 描述
     */
    private String descr;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_schema_name", this.getSchemaName(), map);
        putNoNull("EQ_schema_code", this.getSchemaCode(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
