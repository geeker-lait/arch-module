package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.DatabaseSchemaData;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Slf4j
@Component
public class EntityBuilder extends AbstractBuilder implements Buildable<DatabaseSchemaData> {

    @Override
    public TemplateName getTemplateName() {
        return TemplateName.ENTITY;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties,
                      PomProperties pomProperties, DocumentProperties documentProperties, DatabaseSchemaData schemaData) {
        buildPackageFile(path, engine, projectProperties, pomProperties, documentProperties, schemaData);



    }

//    @Override
//    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, SchemaData schemaData) {
//        buildPackageFile(path, engine, projectProperties, pomProperties, documentProperties, schemaData);
//    }
//
//    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaPatternable databaseProperties) {
//        log.info("EntityBuilder build: {}", path);
//        buildPackageFile(projectProperties.getCover(), path, templateEngine, projectProperties, documentProperties, databaseProperties);
//    }


}
