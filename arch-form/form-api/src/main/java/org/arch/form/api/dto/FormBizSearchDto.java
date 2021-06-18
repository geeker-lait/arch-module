package org.arch.form.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 项目业务(FormBiz) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:42
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormBizSearchDto implements BaseSearchDto {

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
     * 业务名称
     */
    private String bizName;

    /**
     * 业务码
     */
    private String bizCode;

    /**
     * 业务说明
     */
    private String descr;

    /**
     * 排序
     */
    private Integer sorted;

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
        putNoNull("EQ_biz_name", this.getBizName(), map);
        putNoNull("EQ_biz_code", this.getBizCode(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
