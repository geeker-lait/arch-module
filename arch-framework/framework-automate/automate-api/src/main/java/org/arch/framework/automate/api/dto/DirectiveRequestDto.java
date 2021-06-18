package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.automate.api.DirectiveRequest;

import java.io.Serializable;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DirectiveRequestDto implements DirectiveRequest, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 数据源
     *  类属性 复制于 {@link org.arch.framework.automate.generater.properties.DatabaseProperties}
     */
    DatabasePropertiesDto dataSource;

}
