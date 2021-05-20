package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.properties.DependencieProterties;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
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

    @Override
    public void buildModule(Path path, PomProperties pomProperties, SchemaData schemaData) {
        try {
            doBuild(path, pomProperties, schemaData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doBuild(Path path, PomProperties pomProperties, SchemaData schemaData) throws Exception {
        List<PomProperties> modules = pomProperties.getModules();
        if (modules == null || modules.size() == 0) {
            // 创建模块src目录,标准化目录创建
            for (String dir : SRC_DIR) {
                Files.createDirectories(path.resolve(dir));
            }
            pomProperties.setPackaging("jar");
            if (!StringUtils.isEmpty(pomProperties.getDocumentTypes())) {
                List<String> docTyps = Arrays.asList(pomProperties.getDocumentTypes().split(","));
                for (String p : docTyps) {
                    DocumentProperties documentProperties = DOCUMENT_MAP.get(p);
                    // 获取模板
                    String stemplate = documentProperties.getTemplate();
                    Buildable buildable = BUILDER_MAP.get(stemplate);
                    if (buildable == null) {
                        throw new CodegenException("buildable is null ,please implements org.arch.framework.automate.generater.core.Buildable and config it as a spring component");
                    }
                    buildable.build(path, engine, projectProperties, pomProperties, documentProperties, schemaData);
                }
            }
            /*if (null == DEPS.get()) {
                DEPS.set(new ArrayList<>());
            }
            // 收集所有生产的jar类型pom，增加到根pom进行统一版本控制
            DependencieProterties dependencieProterties = new DependencieProterties();
            dependencieProterties.setArtifactId(pomProperties.getArtifactId());
            dependencieProterties.setGroupId(pomProperties.getGroupId());
            dependencieProterties.setVersion("${project.version}");
            DEPS.get().add(dependencieProterties);
            // 创建pom 确保pom 只构建一次
            if (pomBuildOnce) {
                buildPom(cover, path, pomProperties);
            }*/
            return;
        } else {
            for (PomProperties module : modules) {
                pomProperties.setPackaging("pom");
                /**
                 * 之前使用pomPropertiesPatent = pomProperties;多增递归时传递一个参数没有必要
                 * 后来使用 module.setParent(pomProperties)方式是比较理想，但递归会报错，层级太深，未有好但解决方案
                 */
                DependencieProterties parent = new DependencieProterties();
                parent.setArtifactId(pomProperties.getArtifactId());
                parent.setGroupId(pomProperties.getGroupId());
                parent.setVersion(pomProperties.getVersion());
                /**
                 * 继承上级
                 */
                module.setGroupId(pomProperties.getGroupId());
                module.setVersion(pomProperties.getVersion());
                module.setParent(parent);

                Path subPath = path.resolve(module.getArtifactId());
                doBuild(subPath, module, schemaData);
            }
        }
    }


    @Override
    public BuildToolsName getBuildTools() {
        return BuildToolsName.MAVEN;
    }


}
