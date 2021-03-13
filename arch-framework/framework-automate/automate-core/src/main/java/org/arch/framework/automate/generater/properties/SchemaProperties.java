package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class SchemaProperties {
    // schema(xmind/excel/database/json)
    private String typ;
    // schema类型名称 mvc/api
    private String reader;
    // 资源 多个用“,”隔开
    private String resources;
    // 资源为excel时做转化/数据源，当typ 为database时链接数据库
    private Map<String, String> configuration;
    // 自定义yml表,可以是mvc/api表
    private TableProperties tables;

}
