package org.arch.framework.automate.generater.core;

import cn.hutool.extra.template.TemplateEngine;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;

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
     * @param path               路劲
     * @param engine             模板引擎
     * @param projectProperties  项目配置
     * @param pomProperties      包配置
     * @param documentProperties 文档配置
     * @param schemaData         schema配置
     */
    void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, SchemaMetadata schemaData);
}
