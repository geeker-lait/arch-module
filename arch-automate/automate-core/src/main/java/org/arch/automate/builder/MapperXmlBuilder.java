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

@Slf4j
@Component
public class MapperXmlBuilder extends AbstractBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.MAPPER_XML;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {
        buildMvcPackageFile(path, engine, projectProperties, pomProperties, documentProperties, schemaDatas);
    }


    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {

        String fileName = buildFileName(documentProperties, "Mapper", true);
        String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
        Path filePath = Paths.get(path.resolve(Generable.MAIN_RESOURCES).toString().concat(File.separator).concat(fileName).concat(ext));
        buildFile(projectProperties.getCover(), filePath);
        Map<String, Object> dataMap = buildData(projectProperties, documentProperties, null);
        dataMap.put("package", buildPkg(filePath));
        dataMap.put("mainClass", fileName);
        // 获取模板并渲染
        String code = templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap);
        // 写入文件
        try {
            Files.write(filePath, code.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
