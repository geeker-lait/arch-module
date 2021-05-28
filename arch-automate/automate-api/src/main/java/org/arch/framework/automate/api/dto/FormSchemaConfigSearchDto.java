package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 项目配置(FormSchemaConfig) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:50
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormSchemaConfigSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * id主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long schemaId;

    /**
     * 技术框架
     */
    private String mvnJson;

    /**
     * 开发环境配置
     */
    private String devops;

    /**
     * 仓库地址
     */
    private String git;

    /**
     * docker配置
     */
    private String docker;

    /**
     * 描述
     */
    private String descr;

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
        putNoNull("EQ_schema_id", this.getSchemaId(), map);
        putNoNull("EQ_mvn_json", this.getMvnJson(), map);
        putNoNull("EQ_devops", this.getDevops(), map);
        putNoNull("EQ_git", this.getGit(), map);
        putNoNull("EQ_docker", this.getDocker(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
