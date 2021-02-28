package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public class ApiBuilder  extends AbstractBuilder implements Buildable {
    @Override
    public TemplateName getTemplateName() {
        return TemplateName.API;
    }

    @Override
    public void build(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) throws IOException {
        buildPackageFile(projectProperties.getCover(), path, templateEngine, projectProperties, packageProperties, databaseProperties);
    }
}
