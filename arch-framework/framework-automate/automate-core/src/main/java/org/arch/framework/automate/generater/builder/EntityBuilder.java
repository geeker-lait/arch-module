package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Component
public class EntityBuilder extends AbstractBuilder implements Buildable {

    @Override
    public TemplateName getTemplateName() {
        return TemplateName.ENTITY;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaData schemaData) {
        build(path, engine, projectProperties, documentProperties, (DatabaseProperties) schemaData);
    }

    private void build(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, DatabaseProperties databaseProperties){
        log.info("EntityBuilder build: {}",path);
        buildPackageFile(projectProperties.getCover(), path, templateEngine, projectProperties, documentProperties, databaseProperties);
    }


}
