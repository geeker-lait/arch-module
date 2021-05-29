package org.arch.automate.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.automate.core.Buildable;
import org.arch.automate.core.Generable;
import org.arch.automate.core.SchemaData;
import org.arch.automate.enums.TemplateName;
import org.arch.automate.properties.DocumentProperties;
import org.arch.automate.properties.PomProperties;
import org.arch.automate.properties.ProjectProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/10/2021 6:53 PM
 */
@Slf4j
@Component
public class YmlBuilder extends AbstractBuilder implements Buildable {

    @Override
    public TemplateName getTemplateName() {
        return TemplateName.YML;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {
        try {
            doBuild(path, engine, projectProperties, pomProperties, documentProperties, schemaDatas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) throws IOException {
        String fileName = buildFileName(documentProperties, "application", false);
        String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
        Path fileDir = path.resolve(Generable.MAIN_RESOURCES);
        Files.createDirectories(fileDir);
        Path filePath = Paths.get(fileDir.toString().concat(File.separator).concat(fileName).concat(ext));
        buildFile(projectProperties.getCover(), filePath);
        Map<String, Object> dataMap = buildData(projectProperties, documentProperties, null);
        // 获取模板并渲染
        String code = templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap);
        // 写入文件
        Files.write(filePath, code.getBytes());
    }


}
