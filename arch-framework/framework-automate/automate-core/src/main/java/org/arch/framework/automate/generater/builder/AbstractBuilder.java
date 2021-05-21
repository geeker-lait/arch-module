package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.common.database.Table;
import org.arch.framework.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.beans.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/23/2021 9:17 PM
 */
@Slf4j
public abstract class AbstractBuilder {
    /**
     * 获取包命名
     *
     * @param filePath
     * @return
     */
    public String buildPkg(Path filePath) {
        String p = filePath.toString();
        int l = p.indexOf(AbstractGenerator.MAIN_JAVA);
        int ll = p.lastIndexOf(File.separator);
        String pkg = p.substring(l + AbstractGenerator.MAIN_JAVA.length(), ll).replaceAll(Matcher.quoteReplacement(File.separator), "\\.");
        return pkg;
    }

    protected String buildFileName(DocumentProperties documentProperties, String defaultFileName, boolean stuffixed) {
        String suffix = (StringUtils.isEmpty(documentProperties.getSuffix()) ? CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, documentProperties.getType()) : documentProperties.getSuffix());
        String bootstrap = documentProperties.getBootstrap();
        defaultFileName = StringUtils.isEmpty(bootstrap) ? defaultFileName : bootstrap;
        if (stuffixed) {
            return defaultFileName + suffix;
        } else {
            return defaultFileName;
        }
    }

    protected void buildFile(boolean cover, Path filePath) {
        try {
            // 写入文件
            if (Files.exists(filePath)) {
                // 是否覆盖
                if (!cover) {
                    log.info("skip {} due to file exists.", filePath);
                    return;
                } else {
                    Files.delete(filePath);
                }
            }
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Map<String, Object> buildData(ProjectProperties projectProperties, DocumentProperties documentProperties, Metadata metadata) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.putAll(JSONUtil.parseObj(projectProperties));
        dataMap.putAll(JSONUtil.parseObj(documentProperties));
        dataMap.putAll(JSONUtil.parseObj(metadata));
        dataMap.put("author", projectProperties.getAuthor());
        dataMap.put("cover", projectProperties.getCover());
        return dataMap;
    }


    protected String getCurrentPkg(ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaData schemaData) {
        // 设置默认包和后缀名
        String pkg = (null == documentProperties.getPkg() ? documentProperties.getType() : documentProperties.getPkg());
        String currentPkg;
        String domain = "";
        if (schemaData.getSchemaPattern() == SchemaPattern.API) {
            domain = schemaData.getInterfac().getName().toLowerCase();
        }
        if (schemaData.getSchemaPattern() == SchemaPattern.MVC) {
            domain = schemaData.getDatabase().getName().toLowerCase();
        }
        // 领域化
        if (projectProperties.getDomain()) {
            currentPkg = projectProperties.getBasePkg().concat("." + domain).concat("." + pkg);
        } else {
            currentPkg = projectProperties.getBasePkg().concat("." + pkg).concat("." + domain);
        }
        return currentPkg;
    }

    protected void buildPackageFile(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties,
                                    PomProperties pomProperties, DocumentProperties documentProperties, SchemaData schemaData) {
        String currentPkg = getCurrentPkg(projectProperties, documentProperties, schemaData);
        Path packPath = path.resolve(Generable.MAIN_JAVA.concat(currentPkg.replaceAll("\\.", Matcher.quoteReplacement(File.separator))));
        try {

            if (schemaData.getSchemaPattern() == SchemaPattern.API) {
//                Files.createDirectories(packPath);
//                // 写入文件
//                //for(MethodProperties api: schemaMetadata.getApis()){
//                //Files.createDirectories(packPath);
//                String fileName = buildFileName(documentProperties, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, domain), true);
//                String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
//                Path filePath = Paths.get(packPath.toString().concat(File.separator).concat(fileName).concat(ext));
//                // 创建文件
//                buildFile(projectProperties.getCover(), filePath);
//                Map<String, Object> dataMap = new HashMap<>();
//                dataMap.putAll(JSONUtil.parseObj(projectProperties));
//                dataMap.putAll(JSONUtil.parseObj(documentProperties));
//                dataMap.putAll(JSONUtil.parseObj(schemaData));
//                dataMap.put("package", currentPkg);
//                dataMap.put("mainClass", fileName);
//                dataMap.put("author", projectProperties.getAuthor());
//                dataMap.put("cover", projectProperties.getCover());
//                // 获取模板并渲染
//                String code = templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap);
//                // 写入文件
//                Files.write(filePath, code.getBytes());
//                //}
            }
            if (schemaData.getSchemaPattern() == SchemaPattern.MVC) {
                Files.createDirectories(packPath);
                for (Table table : schemaData.getDatabase().getTables()) {
                    tableSchemaToCamel(table);
                    String fileName = buildFileName(documentProperties, table.getName(), projectProperties.getStuffixed());
                    String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
                    Path filePath = Paths.get(packPath.toString().concat(File.separator).concat(fileName).concat(ext));
                    // 创建文件
                    buildFile(projectProperties.getCover(), filePath);
                    Map<String, Object> dataMap = buildData(projectProperties, documentProperties, table);
                    dataMap.put("package", currentPkg);
                    dataMap.put("mainClass", fileName);
                    // 获取模板并渲染
                    String code = templateEngine.getTemplate(documentProperties.getTemplate()).render(dataMap);
                    // 写入文件
                    Files.write(filePath, code.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected Table tableSchemaToCamel(Table table) {
        table.setName(StringUtils.toCapitalizeCamelCase(table.getName()));
        table.getColumns().forEach(c -> {
            c.setName(StringUtils.toCamelCase(c.getName()));
            c.setTyp(JdbcTypeUtils.getFieldType(c.getTyp()).getSimpleName());
        });
        return null;
    }
}
