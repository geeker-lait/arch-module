package org.arch.framework.automate.from.dto;

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
 * @date 2021-02-10 15:44:44
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class FormDefinitionSearchDto extends BaseSearchDto {

    /**
     * 表单分类
     */
    private Long id;

    /**
     * schema主键id
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
    private Integer ver;

    /**
     * 表单描述
     */
    private String descr;

    /**
     * 定义json
     */
    private String definitionJson;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

    @Override
    protected void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_category", this.getCategory(), map);
        putNoNull("EQ_form_name", this.getFormName(), map);
        putNoNull("EQ_form_code", this.getFormCode(), map);
        putNoNull("EQ_table_name", this.getTableName(), map);
        putNoNull("EQ_field_id", this.getFieldId(), map);
        putNoNull("EQ_layout_id", this.getLayoutId(), map);
        putNoNull("EQ_ver", this.getVer(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_definition_json", this.getDefinitionJson(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
