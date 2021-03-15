package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;

import org.arch.framework.automate.generater.core.SchemaMetadata;
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
public class ServiceBuilder extends AbstractBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.SERVICE;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaMetadata schemaData) {
        try {
            doBuild(path, engine, projectProperties, documentProperties, (DatabaseProperties) schemaData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, DatabaseProperties databaseProperties) throws IOException {
        log.info("ServiceBuilder build: {}",path);
        buildPackageFile(projectProperties.getCover(), path, templateEngine, projectProperties, documentProperties, databaseProperties);
    }

}
