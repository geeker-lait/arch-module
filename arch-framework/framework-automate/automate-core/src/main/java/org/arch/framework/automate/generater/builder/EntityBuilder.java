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

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EntityBuilder extends AbstractBuilder implements Buildable {

    @Override
    public TemplateName getTemplateName() {
        return TemplateName.ENTITY;
    }

    @Override
    public void build(boolean cover, Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) throws IOException {
        log.info("EntityBuilder build: {}",path);
        buildPackageFile(cover, path, templateEngine, projectProperties, packageProperties, databaseProperties);
    }

    @Override
    public Map<String, Object> build(String fileName, Path filePath, PackageProperties packageProperties, TableProperties tableProperties) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.putAll(JSONUtil.parseObj(tableProperties));
        dataMap.putAll(JSONUtil.parseObj(packageProperties));
        // 包
        dataMap.put("package", buildPkg(filePath));
        // 其他字段
        dataMap.put("", "");
        // imports pack
        dataMap.put("imports", Arrays.asList());
        return dataMap;
    }

}
