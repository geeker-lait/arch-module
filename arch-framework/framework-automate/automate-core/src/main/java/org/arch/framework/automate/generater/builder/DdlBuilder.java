package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.Generable;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
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
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, PomProperties pomProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {
        doBuild(path, engine, projectProperties, documentProperties, schemaDatas);
    }


    private void doBuild(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, List<SchemaData> schemaDatas) {

        schemaDatas.forEach(schemaData -> {
            if (schemaData.getSchemaPattern() == SchemaPattern.MVC) {
                Path fileDir = path.resolve(Generable.MAIN_RESOURCES + File.separator + documentProperties.getPkg());
                String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
                Map<String, Object> dataMap = buildData(projectProperties, documentProperties, null);
                dataMap.put("database", schemaData.getDatabase());
                dataMap.put("tables", schemaData.getDatabase().getTables());
                if (projectProperties.getCover()) {
                    String name = buildFileName(documentProperties, schemaData.getDatabase().getName(), true).toLowerCase();
                    File file = new File(fileDir.toString().concat(File.separator).concat(name).concat(ext));
                    // 获取模板并渲染
                    templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap, file);
                    log.info("{} builded success by ddl builder", file);
                }
            }
        });
    }


}
