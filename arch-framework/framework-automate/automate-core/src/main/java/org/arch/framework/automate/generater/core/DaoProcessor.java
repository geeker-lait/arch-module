package org.arch.framework.automate.generater.core;

import cn.hutool.extra.template.Template;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.*;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
@Slf4j
@Service
public class DaoProcessor extends AbstractProcessor implements TemplateProcessor {


    @Override
    public void build(GeneratorConfig generatorConfig) throws IOException {
        /**
         *     private String path;
         *     private String name;
         *     private String basePkg;
         *     private PomProperties pom;
         */
        ProjectProperties projectProperties = generatorConfig.getProject();
        PomProperties pomProperties = projectProperties.getPom();
        DatabaseProperties databaseProperties = generatorConfig.getDatabase();
        TemplateProperties templateProperties = generatorConfig.getTemplate();
        Map<String, PackageProperties> packagePropertiesMap = generatorConfig.getPackages().stream().collect(Collectors.toMap(PackageProperties::getType, Function.identity()));
        Path rootPath = projectProperties.getProjectRootPath();
        boolean cover = generatorConfig.isCover();
        // 创建根目录
        Files.createDirectories(rootPath);
        buildModule(cover, rootPath, projectProperties, pomProperties, null,packagePropertiesMap, databaseProperties, templateProperties);
    }

    public void buildModule(boolean cover, Path path, ProjectProperties projectProperties, PomProperties pomProperties,PomProperties pomPropertiesPatent, Map<String, PackageProperties> packagePropertiesMap, DatabaseProperties databaseProperties, TemplateProperties templateProperties) throws IOException {
        List<PomProperties> modules = pomProperties.getModules();
        // 创建pom
        buildPom(cover, path, pomProperties,pomPropertiesPatent);
        if (modules == null) {
            // 创建模块src目录,可不创建最后一起创建，这里为了标准化目录创建一下
            for (String dir : srcDirectorys) {
                Files.createDirectories(path.resolve(dir));
            }

            String basePkg = null == projectProperties.getBasePkg() ? "" : projectProperties.getBasePkg();
            if (!StringUtils.isEmpty(pomProperties.getPackageTypes())) {
                for (String p : Arrays.asList(pomProperties.getPackageTypes().split(","))) {
                    PackageProperties packageProperties = packagePropertiesMap.get(p);
                    String pkg = packageProperties.getPkg();
                    pkg = null == pkg ? "" : pkg;
                    Path _path = path.resolve(MAIN_JAVA.concat(basePkg).concat("." + pkg).replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
                    if (!Files.exists(_path)) {
                        Files.createDirectories(_path);
                    }
                    // 写入文件
                    for (TableProperties tableProperties : databaseProperties.getTables()) {
                        buildFile(cover, _path, packageProperties, tableProperties, templateProperties);
                    }
                }
            }
            return;
        }
        for (PomProperties module : modules) {
            Path subPath = path.resolve(module.getArtifactId());
            pomPropertiesPatent = pomProperties;
            buildModule(cover, subPath, projectProperties, module, pomPropertiesPatent,packagePropertiesMap, databaseProperties, templateProperties);
        }
    }

    private void buildPom(boolean cover, Path path, PomProperties pomProperties,PomProperties pomPropertiesPatent) throws IOException {
        Files.createDirectories(path);
        Path pomFilePath = Paths.get(path.toFile().getAbsolutePath().concat(File.separator).concat("pom.xml"));
        // 写入dao文件
        if (Files.exists(pomFilePath)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", "pom.xml");
                return;
            } else {
                Files.delete(pomFilePath);
            }
        }

        if (pomProperties.getParent() == null  && pomPropertiesPatent != null) {
            DependencieProterties parent = new DependencieProterties();
            parent.setArtifactId(pomPropertiesPatent.getArtifactId());
            parent.setGroupId(pomPropertiesPatent.getGroupId());
            parent.setVersion(pomPropertiesPatent.getVersion());
            pomProperties.setGroupId(pomPropertiesPatent.getGroupId());
            pomProperties.setVersion(pomPropertiesPatent.getVersion());
            pomProperties.setParent(parent);
        }

        Files.createFile(pomFilePath);
        // 获取模板
        Template template = getTemplateEngine().getTemplate("pom.ftl");
        // 渲染模板
        String code = template.render(JSONUtil.parseObj(pomProperties));
        // 写入文件
        Files.write(pomFilePath, code.getBytes());
    }

    public void buildFile(boolean cover, Path path, PackageProperties packageProperties, TableProperties tableProperties, TemplateProperties templateProperties) throws IOException {
        // 处理文件名，获取dao文件名称并转驼峰
        String fileName = StringUtils.toCapitalizeCamelCase(tableProperties.getName());
        String suffix = packageProperties.getSuffix();
        String ext = packageProperties.getExt();
        if (!StringUtils.isEmpty(suffix)) {
            fileName = fileName + StringUtils.toCapitalizeCamelCase(suffix);
        }
        if (!StringUtils.isEmpty(ext)) {
            fileName = fileName + packageProperties.getExt();
        }
        Path daoFilePath = Paths.get(path.toFile().getAbsolutePath().concat(File.separator).concat(fileName));
        // 写入dao文件
        if (Files.exists(daoFilePath)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", fileName);
                return;
            } else {
                Files.delete(daoFilePath);
            }
        }
        Files.createFile(daoFilePath);
        // 获取模板
        Template template = getTemplateEngine().getTemplate(packageProperties.getTemplate());
        // 渲染模板
        String code = template.render(JSONUtil.parseObj(tableProperties));
        // 写入文件
        Files.write(daoFilePath, code.getBytes());
    }


    @Override
    public TemplateName getTemplate() {
        return TemplateName.DAO;
    }

    @Override
    public DataProperties buildData() {
        return null;
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createModule(renderingRequest);
        createFile(code, renderingRequest);
    }

    @Override
    public void createModule(RenderingRequest renderingRequest) {
        creatMavenDirectory(renderingRequest);
    }


    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {
        String pack = renderingRequest.getPackageName().replace(".", File.separator);
        renderingRequest.getDatabaseInfos().forEach(databaseInfo -> {
            String mn = databaseInfo.getModuleName();
            databaseInfo.getEntityInfos().forEach(entityInfo -> {
                try {
                    saveToFile(code, renderingRequest.getSavePath() + File.separator + mn + File.separator + MAIN_JAVA + pack + File.separator + mn + File.separator + "dao", entityInfo.getTableName() + ".java", renderingRequest.isCover());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CodegenException(String.format("render %s code source failed.", renderingRequest.getEntity().getClassName()), e);
                }
            });
        });
    }

}
