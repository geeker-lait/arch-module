package org.arch.framework.automate.generater.core;

import cn.hutool.extra.template.TemplateEngine;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO 40599365 胡
 * @weixin PN15855012581
 * @date 2/22/2021 6:17 PM
 */
public interface Buildable {
    /**
     * 获取模板名称
     *
     * @return
     */
    TemplateName getTemplateName();

    /**
     * 构架模板文件
     *
     * @param path               路劲
     * @param templateEngine     模板引擎
     * @param projectProperties  项目配置
     * @param documentProperties 包配置
     * @param databaseProperties 数据库配置
     */
    void build(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, DatabaseProperties databaseProperties) throws IOException;

    /**
     * @param path
     * @param engine
     * @param projectProperties
     * @param documentProperties
     * @param schemaProperties
     */
    void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaProperties schemaProperties);
}
