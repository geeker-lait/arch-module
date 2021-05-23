package org.arch.framework.automate.generater.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.arch.framework.automate.generater.core.ConfigProperties;
import org.arch.framework.automate.generater.core.SchemaConfiguration;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.common.configuration.DatabaseConfiguration;
import org.arch.framework.automate.common.configuration.ExcelFiledConfiguration;
import org.arch.framework.automate.common.configuration.XmindConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/12/2021 6:44 PM
 */
@ToString
public class SchemaProperties implements ConfigProperties {
    // schema(xmind/excel/database/json)
    @Setter
    @Getter
    private String typ;
    @Setter
    @Getter
    // schema模式 mvc/api/mvc,api
    private String patterns;
    @Setter
    @Getter
    // 资源 多个用“,”隔开
    private String resources;
    @Setter
    // 资源为excel时做转化/数据源，当typ 为database时链接数据库
    private Map<String, String> configuration = new HashMap<>();


    public SchemaConfiguration getSchemaConfiguration() {
        // 转化为database配置
        if (SchemaType.DATABASE.name().equalsIgnoreCase(typ)) {
            DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
            databaseConfiguration.setDriver(configuration.get("driver"));
            databaseConfiguration.setPassword(configuration.get("password"));
            databaseConfiguration.setUrl(configuration.get("url"));
            databaseConfiguration.setPort(configuration.get("port"));
            databaseConfiguration.setUsername(configuration.get("username"));
            databaseConfiguration.setDatabase(resources);
            return databaseConfiguration;
            // 转换为excle配置
        } else if (SchemaType.EXCEL.name().equalsIgnoreCase(typ)) {
            ExcelFiledConfiguration excelFiledConfiguration = new ExcelFiledConfiguration();
            excelFiledConfiguration.getHeader().putAll(configuration);
            return excelFiledConfiguration;
            // 转化为xmind配置
        } else if (SchemaType.XMIND.name().equalsIgnoreCase(typ)) {
            // 暂时无转换
            XmindConfiguration xmindConfiguration = new XmindConfiguration();
            return xmindConfiguration;
        }
        return null;
    }

    public SchemaProperties() {

    }


}
