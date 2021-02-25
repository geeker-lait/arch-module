package org.arch.framework.automate.generater.core;

import cn.hutool.extra.template.Template;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.properties.*;
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
import java.util.regex.Matcher;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service
public class MavenGenerator extends AbstractGenerator {

    public void buildModule(Path path, ProjectProperties projectProperties, PomProperties pomProperties, PomProperties pomPropertiesPatent, DatabaseProperties databaseProperties) throws IOException {
        List<PomProperties> modules = pomProperties.getModules();
        // 创建pom
        buildPom(cover, path, pomProperties, pomPropertiesPatent);
        if (modules == null) {
            // 创建模块src目录,可不创建最后一起创建，这里为了标准化目录创建一下
            for (String dir : srcDirectorys) {
                Files.createDirectories(path.resolve(dir));
            }
            //String basePkg = null == projectProperties.getBasePkg() ? "" : projectProperties.getBasePkg();
            if (!StringUtils.isEmpty(pomProperties.getPackageTypes())) {
                for (String p : Arrays.asList(pomProperties.getPackageTypes().split(","))) {
                    PackageProperties packageProperties = packagePropertiesMap.get(p);
                    // 获取模板
                    String stemplate = packageProperties.getTemplate();
                    Buildable buildable = builderMap.get(stemplate);
                    if (buildable == null) {
                        throw new CodegenException("buildable is null ,please implements org.arch.framework.automate.generater.core.Buildable and config it as a spring component");
                    }
                    buildable.build(cover,path,engine,projectProperties,packageProperties,databaseProperties);

                    /*Path filePath;
                    // 创建main
                    if (p.equalsIgnoreCase(TemplateName.APPLICATION.name())) {
                        filePath = path.resolve(MAIN_JAVA.concat(basePkg).replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
                        buildFile(cover, filePath, packageProperties, null);
                    } else if (p.equalsIgnoreCase(TemplateName.YML.name())) { // 构建yml
                        filePath = path.resolve(MAIN_RESOURCES);
                        buildFile(cover, filePath, packageProperties, null);
                    } else if (p.equalsIgnoreCase(TemplateName.DDL.name())) {
                        filePath = path.resolve(MAIN_RESOURCES + File.separator + "ddl");
                        // 创建总schema

                        // 创建个table schema
                        buildFile(cover, filePath, packageProperties, null);
                    } else if (p.equalsIgnoreCase(TemplateName.DOCKER.name())) {
                        filePath = path.resolve(MAIN_RESOURCES);
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
                    }*/
                }
            }
            return;
        }
        for (PomProperties module : modules) {
            Path subPath = path.resolve(module.getArtifactId());
            pomPropertiesPatent = pomProperties;
            buildModule(subPath, projectProperties, module, pomPropertiesPatent, databaseProperties);
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
        Template template = engine.getTemplate("pom.ftl");
        // 渲染模板
        String code = template.render(JSONUtil.parseObj(pomProperties));
        // 写入文件
        Files.write(pomFilePath, code.getBytes());
    }


//    private void buildFile(boolean cover, Path path, PackageProperties packageProperties, TableProperties tableProperties) throws IOException {
//        String fileName = null;
//        String suffix = packageProperties.getSuffix();
//        String ext = packageProperties.getExt();
//        String bootstrap = packageProperties.getBootstrap();
//        if (!Files.exists(path)) {
//            Files.createDirectories(path);
//        }
//        // 特殊处理的文件yml ddl docker
//        if (packageProperties.getType().equalsIgnoreCase(TemplateName.YML.name())) {
//            fileName = StringUtils.isEmpty(bootstrap) ? "application" : bootstrap;
//        }
//        if (packageProperties.getType().equalsIgnoreCase(TemplateName.DDL.name())) {
//            fileName = StringUtils.isEmpty(bootstrap) ? "ddl" : bootstrap;
//        }
//        if (packageProperties.getType().equalsIgnoreCase(TemplateName.DOCKER.name())) {
//            fileName = StringUtils.isEmpty(bootstrap) ? "Dockerfile" : bootstrap;
//        }
//        // 处理main
//        if (packageProperties.getType().equalsIgnoreCase(TemplateName.APPLICATION.name())) {
//            fileName = StringUtils.isEmpty(bootstrap) ? "application" : StringUtils.toCapitalizeCamelCase(bootstrap);
//        }
//        // 处理文件名，获取文件名称并转驼峰
//        if (tableProperties != null) {
//            fileName = StringUtils.toCapitalizeCamelCase(tableProperties.getName());
//        }
//        // 后缀不为空，增加后缀
//        if (!StringUtils.isEmpty(suffix)) {
//            fileName = fileName + StringUtils.toCapitalizeCamelCase(suffix);
//        }
//        // 扩展名不为空，增加扩展名
//        if (!StringUtils.isEmpty(ext)) {
//            fileName = fileName + packageProperties.getExt();
//        }
//        Path filePath = Paths.get(path.toFile().getAbsolutePath().concat(File.separator).concat(fileName));
//        // 写入文件
//        if (Files.exists(filePath)) {
//            // 是否覆盖
//            if (!cover) {
//                log.info("skip {} due to file exists.", fileName);
//                return;
//            } else {
//                Files.delete(filePath);
//            }
//        }
//        Files.createFile(filePath);
//        // 获取模板
//        String stemplate = packageProperties.getTemplate();
//        Buildable buildable = builderMap.get(stemplate);
//        Map<String, Object> dataMap = buildable.build(fileName, filePath, packageProperties, tableProperties);
//        // 渲染模板
//        Template template = engine.getTemplate(stemplate);
//        String code = template.render(dataMap);
//        // 写入文件
//        Files.write(filePath, code.getBytes());
//    }

}
