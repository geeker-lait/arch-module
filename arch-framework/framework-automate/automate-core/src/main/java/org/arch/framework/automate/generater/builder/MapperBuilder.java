package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.AbstractGenerator;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Slf4j
@Component
public class MapperBuilder extends AbstractBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.MAPPER;
    }

    @Override
    public void build(boolean cover, Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) throws IOException {
        buildPackageFile(cover, path, templateEngine, projectProperties, packageProperties, databaseProperties);
    }

    @Override
    public Map<String, Object> build(String fileName, Path filePath, PackageProperties packageProperties, TableProperties tableProperties) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.putAll(JSONUtil.parseObj(tableProperties));
        dataMap.putAll(JSONUtil.parseObj(packageProperties));
        log.info("package :{}", packageProperties.getPkg());
        String p = filePath.toString();
        int l = p.indexOf(AbstractGenerator.MAIN_JAVA);
        int ll = p.lastIndexOf(File.separator);
        String pkg = p.substring(l + AbstractGenerator.MAIN_JAVA.length(), ll).replaceAll(Matcher.quoteReplacement(File.separator), "\\.");
        dataMap.put("package", pkg);
        dataMap.put("stuffix", packageProperties.getSuffix());
        dataMap.put("", "");
        return dataMap;
    }
}
