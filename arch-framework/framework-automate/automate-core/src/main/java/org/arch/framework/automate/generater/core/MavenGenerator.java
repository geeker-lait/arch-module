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
            if (!StringUtils.isEmpty(pomProperties.getDocumentTypes())) {
                for (String p : Arrays.asList(pomProperties.getDocumentTypes().split(","))) {
                    PackageProperties packageProperties = packagePropertiesMap.get(p);
                    // 获取模板
                    String stemplate = packageProperties.getTemplate();
                    Buildable buildable = builderMap.get(stemplate);
                    if (buildable == null) {
                        throw new CodegenException("buildable is null ,please implements org.arch.framework.automate.generater.core.Buildable and config it as a spring component");
                    }
                    buildable.build(path,engine,projectProperties,packageProperties,databaseProperties);
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
        // 写入文件
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

}
