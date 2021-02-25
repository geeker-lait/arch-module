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
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/24/2021 4:18 PM
 */
@Slf4j
@Component
public class DdlBuilder extends AbstractBuilder implements Buildable {
    @Override
    public TemplateName getTemplateName() {
        return TemplateName.DDL;
    }

    @Override
    public void build(boolean cover, Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) {
        log.info("DdlBuilder start");
    }

}
