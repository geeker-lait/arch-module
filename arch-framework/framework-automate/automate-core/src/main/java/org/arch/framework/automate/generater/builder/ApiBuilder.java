package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.properties.*;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service
public class ApiBuilder  extends AbstractBuilder implements Buildable {
    @Override
    public TemplateName getTemplateName() {
        return TemplateName.API;
    }

    @Override
    public void build(Path path, TemplateEngine engine, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaMetadata schemaData) {
        doBuild(path,engine,projectProperties,documentProperties,(XmindProperties)schemaData);
        log.info("api builder building {}", schemaData);
    }

    private void doBuild(Path path, TemplateEngine engine, ProjectProperties projectProperties, DocumentProperties documentProperties, XmindProperties schemaData) {
        buildPackageFile(projectProperties.getCover(), path, engine, projectProperties, documentProperties, databaseProperties);
    }



    protected void buildApiPackageFile(boolean cover, Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, DocumentProperties documentProperties, DatabaseProperties databaseProperties) {
        // 设置默认包和后缀名
        String pkg = (null == documentProperties.getPkg() ? documentProperties.getType() : documentProperties.getPkg());
        String currentPkg;
        // 领域化
        if (projectProperties.getDomain()) {
            currentPkg = projectProperties.getBasePkg().concat("." + databaseProperties.getName().toLowerCase()).concat("." + pkg);
        } else {
            currentPkg = projectProperties.getBasePkg().concat("." + pkg).concat("." + databaseProperties.getName().toLowerCase());
        }
        Path packPath = path.resolve(Generable.MAIN_JAVA.concat(currentPkg.replaceAll("\\.", Matcher.quoteReplacement(File.separator))));
        try {
            Files.createDirectories(packPath);
            // 写入文件
            for (TableProperties tableProperties : databaseProperties.getTables()) {
                String fileName = buildFileName(documentProperties, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, tableProperties.getName()), true);
                String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
                Path filePath = Paths.get(packPath.toString().concat(File.separator).concat(fileName).concat(ext));
                // 创建文件
                buildFile(cover, filePath);
                Map<String, Object> dataMap = buildData(projectProperties, documentProperties, tableProperties);
                dataMap.put("package", currentPkg);
                dataMap.put("mainClass", fileName);
                // 获取模板并渲染
                String code = templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap);
                // 写入文件
                Files.write(filePath, code.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
