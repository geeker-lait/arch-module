package org.arch.framework.automate.generater.core;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.properties.*;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/22/2021 5:44 PM
 */
@Slf4j
public abstract class AbstractGenerator implements Generable {

    protected final static Map<String, PackageProperties> packagePropertiesMap = new HashMap<>();
    protected final static Map<String, Buildable> builderMap = new HashMap<>();
    protected TemplateEngine engine;
    protected Boolean cover;
    @Autowired
    private List<Buildable> builders;

    private void init(GeneratorConfig generatorConfig) {
        //Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        //cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        TemplateProperties templateProperties = generatorConfig.getTemplate();
        TemplateConfig.ResourceMode resourceMode = EnumUtil.fromString(TemplateConfig.ResourceMode.class, StringUtils.upperCase(templateProperties.getResourceMode()));
        TemplateConfig templateConfig = new TemplateConfig(templateProperties.getDir(), resourceMode);
        engine = TemplateUtil.createEngine(templateConfig);
        packagePropertiesMap.putAll(generatorConfig.getPackages().stream().collect(Collectors.toMap(PackageProperties::getType, Function.identity())));
        builderMap.putAll(builders.stream().collect(Collectors.toMap(b -> b.getTemplateName().getTemplate(), Function.identity())));
        cover = generatorConfig.getCover();
    }


    /**
     * private String path;
     * private String name;
     * private String basePkg;
     * private PomProperties pom;
     *
     * @param generatorConfig
     * @throws IOException
     */
    @Override
    public void generate(GeneratorConfig generatorConfig) throws IOException {
        ProjectProperties projectProperties = generatorConfig.getProject();
        PomProperties pomProperties = projectProperties.getPom();
        DatabaseProperties databaseProperties;
        // 确定生成源
        if (StringUtils.isEmpty(generatorConfig.getSource())) {
            throw new CodegenException("must assign source");
        }
        // 默认支持database,如果是excel 转换为database
        if (generatorConfig.getSource().equalsIgnoreCase("database")) {
            databaseProperties = generatorConfig.getDatabase();
        } else if (generatorConfig.getSource().equalsIgnoreCase("excel")) {
            // 转换为 database
            databaseProperties = null;
        } else if (generatorConfig.getSource().equalsIgnoreCase("json")) {
            // 转换为 database
            databaseProperties = null;
        } else {
            throw new CodegenException("not support source");
        }
        // 初始化
        init(generatorConfig);
        // 创建根目录
        Path rootPath = projectProperties.getProjectRootPath();
        Files.createDirectories(rootPath);
        // 创建模块
        buildModule(rootPath, projectProperties, pomProperties, null, databaseProperties);
    }


    public abstract void buildModule(Path path, ProjectProperties projectProperties, PomProperties pomProperties, PomProperties pomPropertiesPatent, DatabaseProperties databaseProperties) throws IOException;


}
