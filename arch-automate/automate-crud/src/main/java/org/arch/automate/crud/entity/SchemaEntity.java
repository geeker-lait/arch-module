package org.arch.automate.crud.entity;

import lombok.Data;

/**
 * schemas:
 * - typ: xmind
 * patterns: api,mvc
 * resources: classpath:minds/WMS.xmind
 * <p>
 * - typ: excel
 * patterns: mvc
 * resources: classpath:xls/uni-life-ums.xlsx
 * configuration: {
 * table: "表名/Table",
 * column: "列名/Column",
 * type: "数据类型/Type",
 * length: "长度/Length",
 * isnull: "是否为空/Null",
 * defaultValue: "默认值/Default",
 * pk: "是否主键/Primary(Y,N)",
 * fks: "外键/Forigen(可空)",
 * uniques: "是否唯一/Unique(Y,N)",
 * comment: "备注/Comment"
 * }
 * - typ: database
 * patterns: mvc
 * resources: arch_module
 * configuration: {
 * driver: "com.mysql.cj.jdbc.Driver",
 * url: "jdbc:mysql://localhost:3306?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
 * username: "root",
 * password: "root"
 * }
 * - typ: database
 * patterns: api
 * resources: arch_module_api
 * configuration: {
 * driver: "com.mysql.cj.jdbc.Driver",
 * url: "jdbc:mysql://localhost:3306?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
 * username: "root",
 * password: "root"
 * }
 */
@Data
public class SchemaEntity {
    private String typ;
    private String parrerns;
    private String resources;
    private String jsonConfig;


}
