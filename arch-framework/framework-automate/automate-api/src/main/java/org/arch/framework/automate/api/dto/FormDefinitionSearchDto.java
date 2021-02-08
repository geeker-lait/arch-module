package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单定义(FormDefinition) search dto
 *
 * @author lait
 * @date 2021-02-08 13:25:12
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormDefinitionSearchDto implements BaseSearchDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单分类
     */
    private String category;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单code,用作页面表单
     */
    private String formCode;

    /**
     * 关联数据表
     */
    private String tableName;

    /**
     * 表单部署字段ID
     */
    private Long fieldId;

    /**
     * 表单部署布局ID
     */
    private Long layoutId;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 表单描述
     */
    private String descr;

    /**
     * 时间戳
     */
    private LocalDateTime st;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_category", this.getCategory(), map);
        putNoNull("EQ_form_name", this.getFormName(), map);
        putNoNull("EQ_form_code", this.getFormCode(), map);
        putNoNull("EQ_table_name", this.getTableName(), map);
        putNoNull("EQ_field_id", this.getFieldId(), map);
        putNoNull("EQ_layout_id", this.getLayoutId(), map);
        putNoNull("EQ_version", this.getVersion(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
