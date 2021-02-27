package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Service
public class BizBuilder extends AbstractBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.BIZ;
    }

    @Override
    public void build(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) throws IOException {
        log.info("BizBuilder start");
        buildPackageFile(projectProperties.getCover(), path, templateEngine, projectProperties, packageProperties, databaseProperties);
    }

}
