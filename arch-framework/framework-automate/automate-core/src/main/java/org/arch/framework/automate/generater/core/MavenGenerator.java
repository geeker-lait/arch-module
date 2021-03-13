package org.arch.framework.automate.generater.core;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.properties.DependencieProterties;
import org.arch.framework.automate.generater.properties.DocumentProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        build(path, pomProperties, schemaData);
    }

    private void build(Path path, PomProperties pomProperties, SchemaData schemaData) {
        List<PomProperties> modules = pomProperties.getModules();
        if (modules != null) {
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
                buildModule(subPath, module, schemaData);
            }
        } else {
            try {
                // 创建模块src目录,可不创建最后一起创建，这里为了标准化目录创建一下
                for (String dir : srcDirectorys) {
                    Files.createDirectories(path.resolve(dir));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pomProperties.setPackaging("jar");
        if (!StringUtils.isEmpty(pomProperties.getDocumentTypes())) {
            for (String p : Arrays.asList(pomProperties.getDocumentTypes().split(","))) {
                DocumentProperties documentProperties = documentsMap.get(p);
                // 获取模板
                String stemplate = documentProperties.getTemplate();
                Buildable buildable = builderMap.get(stemplate);
                if (buildable == null) {
                    throw new CodegenException("buildable is null ,please implements org.arch.framework.automate.generater.core.Buildable and config it as a spring component");
                }
                buildable.build(path, engine, projectProperties, documentProperties, schemaData);
            }
        }
        if (null == DEPS.get()) {
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
        }
        return;
    }


    private void buildPom(boolean cover, Path path, PomProperties pomProperties) {
        Path pomFilePath = Paths.get(path.toString().concat(File.separator).concat("pom.xml"));
        // 写入文件
        if (Files.exists(pomFilePath)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", path);
                return;
            } else {
                try {
                    Files.delete(pomFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (pomProperties.isRoot()) {
            if (null != pomProperties.getDependencyManagement()) {
                pomProperties.getDependencyManagement().addAll(DEPS.get());
            } else {
                pomProperties.setDependencies(DEPS.get());
            }
            //DEPS.remove();
        }
        JSONObject jsonObject = JSONUtil.parseObj(pomProperties);
        String dt = pomProperties.getDocumentTypes();
        if (!StringUtils.isEmpty(dt)) {
            Optional<String> app = Arrays.asList(dt.split(",")).stream().filter(t -> t.equalsIgnoreCase(TemplateName.APPLICATION.name())).findAny();
            if (app.isPresent()) {
                jsonObject.putOpt(app.get(), true);
            }
        }
        // 获取并渲染模板
        engine.getTemplate("pom.ftl").render(jsonObject, new File(pomFilePath.toString()));
        log.info("\n\ncreate pom file in : {}\npom json :{}\n\n", pomFilePath, pomProperties);
    }

    @Override
    public BuildToolsName getBuildTools() {
        return BuildToolsName.MAVEN;
    }


}
