package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.Generable;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Slf4j
@Component
public class ApplicationBuilder extends AbstractBuilder implements Buildable {

    @Override
    public TemplateName getTemplateName() {
        return TemplateName.APPLICATION;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, SchemaData schemaData) {
        try {
            doBuild(path, engine, projectProperties, documentProperties, schemaData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaData schemaData) throws IOException {
        String basePkg = null == projectProperties.getBasePkg() ? "" : projectProperties.getBasePkg();
        Path fileDir = path.resolve(Generable.MAIN_JAVA.concat(basePkg).replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
        Files.createDirectories(fileDir);
        String suffix = StringUtils.isEmpty(documentProperties.getSuffix()) ? "" : documentProperties.getSuffix();
        String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
        String bootstrap = documentProperties.getBootstrap();
        String fileName = StringUtils.isEmpty(bootstrap) ? "Application" : bootstrap + suffix;
        Path filePath = Paths.get(fileDir.toString().concat(File.separator).concat(fileName).concat(ext));
        // 写入文件
        if (Files.exists(filePath)) {
            // 是否覆盖
            if (!projectProperties.getCover()) {
                log.info("skip {} due to file exists.", fileName);
                return;
            } else {
                Files.delete(filePath);
            }
        }
        Files.createFile(filePath);
        // 渲染模板
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.putAll(JSONUtil.parseObj(documentProperties));
        dataMap.put("package", buildPkg(filePath));
        dataMap.put("mainClass", fileName);
        // 获取模板并渲染
        String code = templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap);
        // 写入文件
        Files.write(filePath, code.getBytes());
    }

}
