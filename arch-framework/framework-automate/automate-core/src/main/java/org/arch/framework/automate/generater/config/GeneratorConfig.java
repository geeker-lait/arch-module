package org.arch.framework.automate.generater.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.arch.framework.automate.generater.properties.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: spring:
 * generator:
 * cover: true
 * author: lait
 * template:
 * dir: src/main/resources/templates
 * project:
 * path:
 * poms:
 * - artifactId:
 * groupId:
 * version:
 * packages: entity,dao,service
 * dependencies:
 * - groupId:
 * artifactId:
 * poms:
 * - artifactId:
 * groupId:
 * version:
 * packages: entity,dao,service
 * packages:
 * - id: entity
 * pkg:
 * template:
 * suffix:
 * - id: mapper
 * pkg:
 * template:
 * suffix:
 * - id: dao
 * pkg:
 * template:
 * suffix:
 * - id: service
 * pkg:
 * template:
 * suffix:
 * - id: controller
 * pkg:
 * template:
 * suffix:
 * - id: dto
 * pkg:
 * template:
 * suffix:
 * database:
 * driver: com.mysql.cj.jdbc.Driver
 * url: "jdbc:mysql://localhost:3306/uni?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
 * username: root
 * password: root
 * </p>
 * {
 * "cover": true,
 * "template": {
 * "dir": "src/main/resources/templates"
 * },
 * "database": {
 * "password": "root",
 * "tables": [{
 * "columns": [{
 * "length": "",
 * "name": "",
 * "typ": ""
 * }],
 * "name": "",
 * "comment": ""
 * }],
 * "driver": "com.mysql.cj.jdbc.Driver",
 * "username": "root"
 * },
 * "author": "lait",
 * "project": {
 * "path": "",
 * "pom": {
 * "groupId": "",
 * "artifactId": "",
 * "version": "",
 * "modules": [{
 * "groupId": "",
 * "artifactId": "",
 * "packages": "entity,dao,service",
 * "version": "",
 * "modules": [{
 * "groupId": "",
 * "artifactId": "",
 * "packages": "entity,dao,service",
 * "version": ""
 * }, {
 * "groupId": "",
 * "artifactId": "",
 * "packages": "entity,dao,service",
 * "version": "",
 * "modules": [{
 * "groupId": "",
 * "artifactId": "",
 * "packages": "entity,dao,service",
 * "version": ""
 * }, {
 * "groupId": "",
 * "artifactId": "",
 * "packages": "entity,dao,service",
 * "version": ""
 * }]
 * }],
 * "dependencies": [{
 * "groupId": "",
 * "artifactId": ""
 * }]
 * }]
 * }
 * },
 * "packages": [{
 * "template": "",
 * "id": "entity",
 * "suffix": "",
 * "pkg": ""
 * }, {
 * "template": "",
 * "id": "mapper",
 * "suffix": "",
 * "pkg": ""
 * }, {
 * "template": "",
 * "id": "dao",
 * "suffix": "",
 * "pkg": ""
 * }, {
 * "template": "",
 * "id": "service",
 * "suffix": "",
 * "pkg": ""
 * }, {
 * "template": "",
 * "id": "controller",
 * "suffix": "",
 * "pkg": ""
 * }, {
 * "template": "",
 * "id": "dto",
 * "suffix": "",
 * "pkg": ""
 * }]
 * }
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.generator")
public class GeneratorConfig {

    /*
     * cover : true
     * author : lait
     * template : {"dir":"templates","resource-mode":"classpath"}
     * schema : excel
     * project : {"path":"/Users/lait.zhang/Workspaces/Java/Projects/arch-module","basePkg":"org.arch.gen","pom":{"artifactId":"arch-gen","groupId":"org.arch.projects","version":"0.0.1","modules":[{"artifactId":"arch-gen-core","groupId":null,"version":null,"packageTypes":"entity,dao"},{"artifactId":"arch-gen-common","groupId":null,"version":null,"packageTypes":"dao","dependencies":[{"groupId":null,"artifactId":null}],"modules":[{"artifactId":"arch-gen-common-a","groupId":null,"version":null,"packageIds":"dao"},{"artifactId":"arch-gen-common-b","groupId":null,"version":null,"packageTypes":"entity,dao,service","modules":[{"artifactId":"arch-gen-common-b-1","groupId":null,"version":null,"packageTypes":"entity,dao,service"},{"artifactId":"arch-gen-common-b-2","groupId":null,"version":null,"packageTypes":null}]}]}]}}
     * packages : [{"type":"entity","pkg":"aa.entity","template":"entity.ftl","suffix":"Entity"},{"type":"mapper","pkg":null,"template":"mapper.ftl","suffix":null},{"type":"dao","pkg":"dao","template":"dao.ftl","suffix":"Dao","ext":".java"},{"type":"service","pkg":"service","template":"service.ftl","suffix":null},{"type":"controller","pkg":null,"template":"controller.ftl","suffix":null},{"type":"dto","pkg":null,"template":"dto.ftl","suffix":null}]
     * excel : {"heads":{"表名/Table":"table","列名/Column":"column","数据类型/Type":"type","长度/Length":"length","是否为空/Null(Y,N)":"isnull","默认值/Default":"defaultValue","是否主键/Primary(Y,N)":"primaryKey","是否唯一/Unique(Y,N)":"unique","外键/Forigen(可空)":"forienKey","备注/Comment":"comment"}}
     * database : {"name":"arch","driver":"com.mysql.cj.jdbc.Driver","url":"jdbc:mysql://localhost:3306/uni?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai","username":"root","password":"root","tables":[{"name":"user","comment":"用户表","columns":[{"name":"id","typ":"bigint","length":19,"comment":"主键id"},{"name":"name","typ":"varchar","length":32,"comment":"用户名"},{"name":"age","typ":"int","length":2,"comment":"年龄"}]}]}
     */
    /**
     * 构建工具，maven gradle node....
     */
    private String buildTool;
    /**
     * 源，指定是database/excel/json生成项目，默认数据库
     */
    private String source = "database";
    /**
     * 模版配置
     */
    @NestedConfigurationProperty
    private TemplateProperties template;
    /**
     * 项目配置
     */
    @NestedConfigurationProperty
    private ProjectProperties project;
    /**
     * 包集合
     */
    @NestedConfigurationProperty
    private List<PackageProperties> documents;
    /**
     * json 数据源
     */
    @NestedConfigurationProperty
    private JsonProperties json;
    /**
     * Excel数据源 生产项目
     */
    @NestedConfigurationProperty
    private ExcelProperties excel;
    /**
     * Db数据源 生产项目
     */
    @NestedConfigurationProperty
    private DatabaseProperties database;

}
