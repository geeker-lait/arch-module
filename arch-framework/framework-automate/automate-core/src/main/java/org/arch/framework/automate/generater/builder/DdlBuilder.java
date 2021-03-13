package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.Generable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
    public void build(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, DatabaseProperties databaseProperties) throws IOException {

        Path fileDir = path.resolve(Generable.MAIN_RESOURCES + File.separator + documentProperties.getPkg());
        String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
        Map<String, Object> dataMap = buildData(projectProperties, documentProperties, null);
        dataMap.put("database", databaseProperties.getName());
        dataMap.put("tables",databaseProperties.getTables());
        if(projectProperties.getCover()) {
            String name = buildFileName(documentProperties, databaseProperties.getName(), true).toLowerCase();
            File file = new File(fileDir.toString().concat(File.separator).concat(name).concat(ext));
            // 获取模板并渲染
            templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap, file);
            log.info("{} builded success by ddl builder",file);
        }
    }


    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaProperties schemaProperties) {

    }
}
