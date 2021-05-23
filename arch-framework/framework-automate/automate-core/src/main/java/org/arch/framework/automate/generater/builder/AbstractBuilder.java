package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.database.Table;
import org.arch.framework.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private GeneratorConfig generatorConfig;
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
        dataMap.put("documents",generatorConfig.getDocuments());
        dataMap.put("author", projectProperties.getAuthor());
        dataMap.put("cover", projectProperties.getCover());
        return dataMap;
    }


    protected String buildAndGetCurrentPkg(Path path, ProjectProperties projectProperties, DocumentProperties documentProperties, SchemaData schemaData) throws IOException {
        // 创建模块src目录,标准化目录创建
        for (String dir : Generable.SRC_DIR) {
            Files.createDirectories(path.resolve(dir));
        }
        // 设置默认包和后缀名
        String pkg = StringUtils.isNoneBlank(documentProperties.getPkg()) ? documentProperties.getPkg() : documentProperties.getType();
        String currentPkg;
        String domain = "";
        if (schemaData.getSchemaPattern() == SchemaPattern.API) {
            domain = schemaData.getApi().getName().toLowerCase();
        }
        if (schemaData.getSchemaPattern() == SchemaPattern.MVC) {
            domain = schemaData.getDatabase().getName().toLowerCase();
        }
        domain = domain.replaceAll("-", "");
        // 领域化
        if (projectProperties.getDomain()) {
            currentPkg = projectProperties.getBasePkg().concat("." + domain).concat("." + pkg);
        } else {
            currentPkg = projectProperties.getBasePkg().concat("." + pkg).concat("." + domain);
        }
        //String currentPkg = getCurrentPkg(projectProperties, documentProperties, schemaData);
        Path packPath = path.resolve(Generable.MAIN_JAVA.concat(currentPkg.replaceAll("\\.", Matcher.quoteReplacement(File.separator))));
        Files.createDirectories(packPath);

        return currentPkg;
    }

    protected void buildMvcPackageFile(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties,
                                       PomProperties pomProperties, DocumentProperties documentProperties, SchemaData schemaData) {
        try {
            if (schemaData.getSchemaPattern() == SchemaPattern.MVC) {
                String currentPkg = buildAndGetCurrentPkg(path, projectProperties, documentProperties, schemaData);
                Path cpath = path.resolve(Generable.MAIN_JAVA.concat(currentPkg.replaceAll("\\.", Matcher.quoteReplacement(File.separator))));
                for (Table table : schemaData.getDatabase().getTables()) {
                    tableSchemaToCamel(table);
                    String fileName = buildFileName(documentProperties, table.getName(), projectProperties.getStuffixed());
                    String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
                    Path filePath = Paths.get(cpath.toString().concat(File.separator).concat(fileName).concat(ext));
                    // 创建文件
                    buildFile(projectProperties.getCover(), filePath);
                    Map<String, Object> dataMap = buildData(projectProperties, documentProperties, table);
                    dataMap.put("package", currentPkg);

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


    protected void buildApiPackageFile(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties,
                                       PomProperties pomProperties, DocumentProperties documentProperties, SchemaData schemaData) {
        try {
            if (schemaData.getSchemaPattern() == SchemaPattern.API) {
                String currentPkg = buildAndGetCurrentPkg(path, projectProperties, documentProperties, schemaData);
                Path cpath = path.resolve(Generable.MAIN_JAVA.concat(currentPkg.replaceAll("\\.", Matcher.quoteReplacement(File.separator))));
                // 写入文件
                for (Interfac interfac : schemaData.getApi().getInterfaces()) {
                    String fileName = buildFileName(documentProperties, interfac.getName(), projectProperties.getStuffixed());
                    String ext = StringUtils.isEmpty(documentProperties.getExt()) ? "" : documentProperties.getExt();
                    Path filePath = Paths.get(cpath.toString().concat(File.separator).concat(fileName).concat(ext));
                    // 创建文件
                    buildFile(projectProperties.getCover(), filePath);
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.putAll(JSONUtil.parseObj(projectProperties));
                    dataMap.putAll(JSONUtil.parseObj(documentProperties));
                    dataMap.putAll(JSONUtil.parseObj(interfac));
                    dataMap.put("package", currentPkg);
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
