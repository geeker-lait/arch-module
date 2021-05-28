package org.arch.form.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 业务表单(FormTable) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:51
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormTableSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * id主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 表单实力id
     */
    private Long tableInstanceId;

    /**
     * 是否逻辑删除
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
        putNoNull("EQ_biz_id", this.getBizId(), map);
        putNoNull("EQ_table_instance_id", this.getTableInstanceId(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
