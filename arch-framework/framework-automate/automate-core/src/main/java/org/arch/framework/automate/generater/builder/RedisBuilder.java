package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.nio.file.Path;
import java.util.Map;

//@Slf4j
//@Component
public class RedisBuilder implements Buildable {

    @Override
    public TemplateName getTemplateName() {
        return null;
    }

    @Override
    public void build(boolean cover, Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) {

    }

    @Override
    public Map<String, Object> build(String fileName, Path filePath, PackageProperties packageProperties, TableProperties tableProperties) {
        return null;
    }
}
