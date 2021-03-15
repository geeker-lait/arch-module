package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.ConfigProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/12/2021 6:44 PM
 */
@Data
@NoArgsConstructor
@ToString
public class SchemaProperties  implements ConfigProperties {
    // schema(xmind/excel/database/json)
    private String typ;
    // schema模式 mvc/api/mvc,api
    private String patterns;
    // 资源 多个用“,”隔开
    private String resources;
    // 资源为excel时做转化/数据源，当typ 为database时链接数据库
    private Map<String, String> configuration = new HashMap<>();
    // 自定义yml表,可以是mvc/api表
    private TableProperties tables;


    public <T extends ConfigProperties> T buildSchemaProperties(){
        if (typ.equalsIgnoreCase("database")) {

            DatabaseProperties databaseProperties = new DatabaseProperties();
            databaseProperties.setName("");
            return (T) databaseProperties;

        } else if (typ.equalsIgnoreCase("excel")) {

            return null;

        } else if (typ.equalsIgnoreCase("xmind")) {

            return null;

        } else if (typ.equalsIgnoreCase("ddl")) {

            return null;
        }
        return null;
    }

}
