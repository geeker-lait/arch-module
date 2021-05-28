package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目配置(FormSchemaConfig) request
 *
 * @author lait
 * @date 2021-02-10 15:55:50
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormSchemaConfigRequest implements Serializable {
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

}
