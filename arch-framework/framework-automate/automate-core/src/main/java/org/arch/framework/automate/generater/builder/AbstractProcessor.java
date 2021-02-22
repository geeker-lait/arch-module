package org.arch.framework.automate.generater.builder;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.config.properties.*;
import org.arch.framework.automate.generater.render.RenderingRequest;
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
 * @date 12/20/2020 9:57 AM
 */
@Slf4j

public abstract class AbstractProcessor implements TemplateProcessor {

    protected final static String MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
    protected final static String MAIN_RESOURCES = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    protected final static String TEST_JAVA = "src" + File.separator + "test" + File.separator + "java" + File.separator;
    protected final static String TEST_RESOURCES = "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    protected final static List<String> srcDirectorys = Arrays.asList(MAIN_JAVA, MAIN_RESOURCES, TEST_JAVA, TEST_RESOURCES);
    protected final static Map<String, PackageProperties> packagePropertiesMap = new HashMap<>();
    private TemplateEngine engine;
    @Autowired
    private GeneratorConfig generatorConfig;

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
        packagePropertiesMap.putAll(generatorConfig.getPackages().stream().collect(Collectors.toMap(PackageProperties::getType, Function.identity())));
        Path rootPath = projectProperties.getProjectRootPath();
        boolean cover = generatorConfig.isCover();
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


    /**
     * 创建模块
     *
     * @param renderingRequest
     */
    public abstract void createModule(RenderingRequest renderingRequest);

    /**
     * 创建文件
     *
     * @param code
     * @param renderingRequest
     */
    public abstract void createFile(String code, RenderingRequest renderingRequest);


    /**
     * 创建maven
     * 这里该用从yaml读取结构
     *
     * @param renderingRequest
     */
    public Map<String, Path> creatMavenDirectory(RenderingRequest renderingRequest) {
        Map<String, Path> pathMap = new HashMap<>();
        // 构架模块根目录
        Path rootPath = Paths.get(renderingRequest.getSavePath() + File.separator + renderingRequest.getModuleName());
        // 构建src/java
        Path srcJava = rootPath.resolve(MAIN_JAVA);
        // 构建src/resources
        Path srcResources = rootPath.resolve(MAIN_RESOURCES);
        // 构建test/java
        Path testJava = rootPath.resolve(TEST_JAVA);
        // 构建test/resources
        Path testResources = rootPath.resolve(TEST_RESOURCES);

        pathMap.put("rootPath", rootPath);
        pathMap.put("srcJava", srcJava);
        pathMap.put("srcResources", srcResources);
        pathMap.put("testJava", testJava);
        pathMap.put("testResources", testResources);

        pathMap.forEach((k, v) -> {
            try {
                Files.createDirectories(v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return pathMap;
    }

    /**
     * 保存文件
     *
     * @param code
     * @param filePath
     * @param fileName
     * @param cover
     * @throws IOException
     */
    public void saveToFile(String code, String filePath, String fileName, boolean cover) throws IOException {
        String finalFileName = filePath + File.separator + fileName;
        // 这里有一个bug mac/linux 下没有盘符的问题,这里需要处理一下
        //new File(new File(“C:/a/b.txt”).getPath().substring(1)).toPath(), “c/a/b.txt”
        Path path = Paths.get(finalFileName);
        if (Files.exists(path)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", fileName);
                return;
            } else {
                Files.delete(path);
            }
        }

        Path dirPath = Paths.get(filePath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        Files.createFile(path);
        Files.write(path, code.getBytes());
        log.info("file path: {}", path);
    }
}
