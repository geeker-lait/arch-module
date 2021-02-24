package org.arch.framework.automate.generater.core;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.properties.*;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/22/2021 5:44 PM
 */
@Slf4j
public abstract class AbstractGenerator implements Generable {

    public final static String MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
    public final static String MAIN_RESOURCES = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    public final static String TEST_JAVA = "src" + File.separator + "test" + File.separator + "java" + File.separator;
    public final static String TEST_RESOURCES = "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    protected final static List<String> srcDirectorys = Arrays.asList(MAIN_JAVA, MAIN_RESOURCES, TEST_JAVA, TEST_RESOURCES);
    protected final static Map<String, PackageProperties> packagePropertiesMap = new HashMap<>();
    protected final static Map<String, Buildable> builderMap = new HashMap<>();
    private TemplateEngine engine;
    @Autowired
    private GeneratorConfig generatorConfig;
    @Autowired
    private List<Buildable> builders;

    public TemplateEngine getTemplateEngine() {
        if (engine == null) {
            TemplateProperties templateProperties = generatorConfig.getTemplate();
            TemplateConfig.ResourceMode resourceMode = EnumUtil.fromString(TemplateConfig.ResourceMode.class, StringUtils.upperCase(templateProperties.getResourceMode()));
            TemplateConfig templateConfig = new TemplateConfig(templateProperties.getDir(), resourceMode);
            engine = TemplateUtil.createEngine(templateConfig);
        }
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
//        cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        return engine;
    }


    @Override
    public void generate(GeneratorConfig generatorConfig) throws IOException {
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
        packagePropertiesMap.putAll(generatorConfig.getPackages().stream().collect(Collectors.toMap(PackageProperties::getType, Function.identity())));
        Path rootPath = projectProperties.getProjectRootPath();
        boolean cover = generatorConfig.getCover();
        // 创建根目录
        Files.createDirectories(rootPath);
        buildModule(cover, rootPath, projectProperties, pomProperties, null, packagePropertiesMap, databaseProperties, templateProperties);
    }

    private void buildModule(boolean cover, Path path, ProjectProperties projectProperties, PomProperties pomProperties, PomProperties pomPropertiesPatent, Map<String, PackageProperties> packagePropertiesMap, DatabaseProperties databaseProperties, TemplateProperties templateProperties) throws IOException {
        List<PomProperties> modules = pomProperties.getModules();
        // 创建pom
        buildPom(cover, path, pomProperties, pomPropertiesPatent);
        if (modules == null) {
            // 创建模块src目录,可不创建最后一起创建，这里为了标准化目录创建一下
            for (String dir : srcDirectorys) {
                Files.createDirectories(path.resolve(dir));
            }
            String basePkg = null == projectProperties.getBasePkg() ? "" : projectProperties.getBasePkg();
            if (!StringUtils.isEmpty(pomProperties.getPackageTypes())) {
                for (String p : Arrays.asList(pomProperties.getPackageTypes().split(","))) {
                    PackageProperties packageProperties = packagePropertiesMap.get(p);
                    Path filePath;
                    // 创建main
                    if (p.equalsIgnoreCase(TemplateName.APPLICATION.name())) {
                        filePath = path.resolve(MAIN_JAVA.concat(basePkg).replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
                        if (!Files.exists(filePath)) {
                            Files.createDirectories(filePath);
                        }
                        buildFile(cover, filePath, packageProperties, null);
                    } else if (p.equalsIgnoreCase(TemplateName.YML.name())) { // 构建yml
                        filePath = path.resolve(MAIN_RESOURCES);
                        if (!Files.exists(filePath)) {
                            Files.createDirectories(filePath);
                        }
                        buildFile(cover, filePath, packageProperties, null);
                    } else if (p.equalsIgnoreCase(TemplateName.DDL.name())) {
                        filePath = path.resolve(MAIN_RESOURCES);
                        if (!Files.exists(filePath)) {
                            Files.createDirectories(filePath);
                        }
                        buildFile(cover, filePath, packageProperties, null);
                    } else if (p.equalsIgnoreCase(TemplateName.DOCKER.name())) {
                        filePath = path.resolve(MAIN_RESOURCES);
                        if (!Files.exists(filePath)) {
                            Files.createDirectories(filePath);
                        }
                        buildFile(cover, filePath, packageProperties, null);
                    } else {
                        // 如果后缀没有设置，默认为package typ
                        if (StringUtils.isEmpty(packageProperties.getSuffix())) {
                            packageProperties.setSuffix(StringUtils.toCapitalizeCamelCase(p));
                        }
                        String pkg = packageProperties.getPkg();
                        pkg = null == pkg ? "" : pkg;
                        filePath = path.resolve(MAIN_JAVA.concat(basePkg).concat("." + pkg).replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
                        if (!Files.exists(filePath)) {
                            Files.createDirectories(filePath);
                        }
                        // 写入文件
                        for (TableProperties tableProperties : databaseProperties.getTables()) {
                            buildFile(cover, filePath, packageProperties, tableProperties);
                        }
                    }
                }
            }
            return;
        }
        for (PomProperties module : modules) {
            Path subPath = path.resolve(module.getArtifactId());
            pomPropertiesPatent = pomProperties;
            buildModule(cover, subPath, projectProperties, module, pomPropertiesPatent, packagePropertiesMap, databaseProperties, templateProperties);
        }
    }


    private void buildPom(boolean cover, Path path, PomProperties pomProperties, PomProperties pomPropertiesPatent) throws IOException {
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

        if (pomProperties.getParent() == null && pomPropertiesPatent != null) {
            DependencieProterties parent = new DependencieProterties();
            parent.setArtifactId(pomPropertiesPatent.getArtifactId());
            parent.setGroupId(pomPropertiesPatent.getGroupId());
            parent.setVersion(pomPropertiesPatent.getVersion());

            pomProperties.setGroupId(pomPropertiesPatent.getGroupId());
            pomProperties.setVersion(pomPropertiesPatent.getVersion());
            pomProperties.setParent(parent);
        }
        if (pomProperties.getModules() != null && pomProperties.getModules().size() > 0) {
            pomProperties.setPackaging("pom");
        }
        log.info("create pom file in : {}", pomFilePath);
        Files.createFile(pomFilePath);
        // 获取模板
        Template template = getTemplateEngine().getTemplate("pom.ftl");
        // 渲染模板
        String code = template.render(JSONUtil.parseObj(pomProperties));
        // 写入文件
        Files.write(pomFilePath, code.getBytes());
    }


    private void buildFile(boolean cover, Path path, PackageProperties packageProperties, TableProperties tableProperties) throws IOException {
        String fileName = null;
        String suffix = packageProperties.getSuffix();
        String ext = packageProperties.getExt();
        String bootstrap = packageProperties.getBootstrap();
        // 特殊处理的文件yml ddl docker
        if (packageProperties.getType().equalsIgnoreCase(TemplateName.YML.name())) {
            fileName = StringUtils.isEmpty(bootstrap) ? "application" : bootstrap;
        }
        if (packageProperties.getType().equalsIgnoreCase(TemplateName.DDL.name())) {
            fileName = StringUtils.isEmpty(bootstrap) ? "ddl" : bootstrap;
        }
        if (packageProperties.getType().equalsIgnoreCase(TemplateName.DOCKER.name())) {
            fileName = StringUtils.isEmpty(bootstrap) ? "Dockerfile" : bootstrap;
        }
        // 处理main
        if (packageProperties.getType().equalsIgnoreCase(TemplateName.APPLICATION.name())) {
            fileName = StringUtils.isEmpty(bootstrap) ? "application" : StringUtils.toCapitalizeCamelCase(bootstrap);
        }
        // 处理文件名，获取文件名称并转驼峰
        if (tableProperties != null) {
            fileName = StringUtils.toCapitalizeCamelCase(tableProperties.getName());
        }
        // 后缀不为空，增加后缀
        if (!StringUtils.isEmpty(suffix)) {
            fileName = fileName + StringUtils.toCapitalizeCamelCase(suffix);
        }
        // 扩展名不为空，增加扩展名
        if (!StringUtils.isEmpty(ext)) {
            fileName = fileName + packageProperties.getExt();
        }
        Path filePath = Paths.get(path.toFile().getAbsolutePath().concat(File.separator).concat(fileName));
        // 写入文件
        if (Files.exists(filePath)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", fileName);
                return;
            } else {
                Files.delete(filePath);
            }
        }
        Files.createFile(filePath);
        // 获取模板
        String stemplate = packageProperties.getTemplate();
        Template template = getTemplateEngine().getTemplate(stemplate);
        Buildable buildable = builderMap.get(stemplate);
        if (buildable == null) {
            builderMap.putAll(builders.stream().collect(Collectors.toMap(b -> b.getTemplateName().getTemplate(), Function.identity())));
            buildable = builderMap.get(stemplate);
        }
        Map<String, Object> dataMap = buildable.buildData(fileName, filePath, packageProperties, tableProperties);
        // 渲染模板
        String code = template.render(dataMap);
        // 写入文件
        Files.write(filePath, code.getBytes());
    }
}
