package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目配置(FormSchemaConfig) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:08
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_schema_config")
public class FormSchemaConfig extends CrudEntity<FormSchemaConfig> {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId("id")
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

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
