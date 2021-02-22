package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单实例(FormTableInstance) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:52
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormTableInstanceSearchDto implements BaseSearchDto {

    /**
     * schema主键id
     */
    private Long id;

    /**
     * 表单ID
     */
    private Long projectId;

    /**
     * 表单名称
     */
    private Long formDefinitionId;

    /**
     * 表单code,用作页面表单
     */
    private Long interfaceIds;

    /**
     * 表单对应的表ID
     */
    private String formName;

    /**
     * 表单对应数据库中生成的table name
     */
    private String formCode;

    /**
     * 表单描述
     */
    private Long formTableId;

    /**
     * 表单名称
     */
    private String formTableName;

    /**
     * 描述
     */
    private String descr;

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
        putNoNull("EQ_project_id", this.getProjectId(), map);
        putNoNull("EQ_form_definition_id", this.getFormDefinitionId(), map);
        putNoNull("EQ_interface_ids", this.getInterfaceIds(), map);
        putNoNull("EQ_form_name", this.getFormName(), map);
        putNoNull("EQ_form_code", this.getFormCode(), map);
        putNoNull("EQ_form_table_id", this.getFormTableId(), map);
        putNoNull("EQ_form_table_name", this.getFormTableName(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
