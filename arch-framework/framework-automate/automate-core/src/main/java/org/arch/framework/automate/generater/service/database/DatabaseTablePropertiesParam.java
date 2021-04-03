package org.arch.framework.automate.generater.service.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.automate.generater.properties.DatabaseProperties;

/**
 * 根据数据库连接（如果没有给定使用默认的数据库连接 - 可能获取不到数据）和数据库名称 获取数据库属性
 * @author junboXiang
 * @version V1.0
 * 2021-04-03
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseTablePropertiesParam {
    /**
     * 数据库连接信息 - jdbc数据
     */
    private DatabaseProperties databaseProperties;

    /**
     * 数据库名称
     */
    private String databaseName;

}
