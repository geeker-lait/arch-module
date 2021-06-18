package org.arch.automate.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.automate.core.Buildable;
import org.arch.automate.core.SchemaData;
import org.arch.automate.enums.TemplateName;
import org.arch.automate.properties.DocumentProperties;
import org.arch.automate.properties.PomProperties;
import org.arch.automate.properties.ProjectProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class FeignServiceBuilder extends AbstractBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.FEIGN_SERVICE;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {
        try {
            doBuild(path, engine, projectProperties, documentProperties, schemaDatas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) throws IOException {
        // TODO: 2021.5.16
    }

}
