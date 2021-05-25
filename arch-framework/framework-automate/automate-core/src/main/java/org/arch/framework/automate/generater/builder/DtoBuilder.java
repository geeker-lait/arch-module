package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class DtoBuilder extends AbstractBuilder implements Buildable {

    @Override
    public TemplateName getTemplateName() {
        return TemplateName.DTO;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {

    }

}
