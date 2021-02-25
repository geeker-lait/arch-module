package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BizBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.BIZ;
    }

    @Override
    public void build(boolean cover, Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) {
        log.info("BizBuilder start");
    }

}
