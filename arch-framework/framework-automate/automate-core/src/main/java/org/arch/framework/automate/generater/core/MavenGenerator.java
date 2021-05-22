package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.properties.DependencieProterties;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            List<DependencieProterties> dependenciesPropertiesList = new ArrayList<>();
            doBuild(path, pomProperties, dependenciesPropertiesList, schemaData);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void doBuild(Path path, PomProperties pomProperties, List<DependencieProterties> dependenciesPropertiesList, SchemaData schemaData) throws Exception {
        Set<PomProperties> modules = pomProperties.getModules();
        if (modules == null || modules.size() == 0) {
            pomProperties.setPackaging("jar");
            /**
             * 设置manage
             */
            DependencieProterties dependencieProterties = new DependencieProterties();
            dependencieProterties.setGroupId(pomProperties.getGroupId());
            dependencieProterties.setArtifactId(pomProperties.getArtifactId());
            dependencieProterties.setVersion(pomProperties.getVersion());
            dependencieProterties.setModule(true);
            dependenciesPropertiesList.add(dependencieProterties);
            // 根据doc typ构建文件
            pomProperties.getDocumentTyps().forEach(docTyp -> {
                DocumentProperties documentProperties = DOCUMENT_MAP.get(docTyp);
                // 获取模板
                String stemplate = documentProperties.getTemplate();
                Buildable buildable = BUILDER_MAP.get(stemplate);
                if (buildable != null) {
                    buildable.build(path, engine, projectProperties, pomProperties, documentProperties, schemaData);
                } else {
                    log.error("buildable is null ,please implements org.arch.framework.automate.generater.core.Buildable and config it as a spring component");
                }
            });
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

                /**
                 * 创建pom
                 */
                BUILDER_MAP.get("pom.ftl").build(path, engine, projectProperties, pomProperties, DOCUMENT_MAP.get("pom"), schemaData);
                doBuild(path.resolve(module.getArtifactId()), module, dependenciesPropertiesList, schemaData);
            }
            if (pomProperties.isRoot()) {
                /**
                 * 设置dependencyManagement 统一版本管理
                 */
                pomProperties.getDependencyManagement().addAll(dependenciesPropertiesList);
            }
        }

    }


    @Override
    public BuildToolsName getBuildTools() {
        return BuildToolsName.MAVEN;
    }


}
