package org.arch.framework.automate.generater.core;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.properties.*;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
public abstract class AbstractGenerator implements Generable/*, ApplicationContextAware */ {

    protected final static Map<String, DocumentProperties> documentsMap = new HashMap<>();
    protected final static Map<String, Buildable> builderMap = new HashMap<>();
    protected final static Map<String, SchemaReadable> readerMap = new HashMap<>();
    protected final static Map<String, Generable> buildToolsMap = new HashMap<>();
    protected final static ThreadLocal<List<DependencieProterties>> DEPS = new ThreadLocal<>();

    protected TemplateEngine engine;
    protected ProjectProperties projectProperties;
    protected String buildTool;
    protected List<String> schemaTyps;
    protected Boolean cover;
    protected boolean pomBuildOnce = true;

    @Autowired
    private List<Buildable> builders;
    @Autowired
    private List<SchemaReadable> schemaReadables;
    @Autowired
    private List<Generable> generables;


    private void init(GeneratorConfig generatorConfig) {
        //Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        //cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        TemplateProperties templateProperties = generatorConfig.getTemplate();
        TemplateConfig.ResourceMode resourceMode = EnumUtil.fromString(TemplateConfig.ResourceMode.class, StringUtils.upperCase(templateProperties.getResourceMode()));
        TemplateConfig templateConfig = new TemplateConfig(templateProperties.getDir(), resourceMode);
        engine = TemplateUtil.createEngine(templateConfig);


        documentsMap.putAll(generatorConfig.getDocuments().stream().collect(Collectors.toMap(DocumentProperties::getType, Function.identity())));
        builderMap.putAll(builders.stream().collect(Collectors.toMap(b -> b.getTemplateName().getTemplate(), Function.identity())));
        readerMap.putAll(schemaReadables.stream().collect(Collectors.toMap(s -> s.getTyp().name().toLowerCase(), Function.identity())));
        buildToolsMap.putAll(generables.stream().collect(Collectors.toMap(s -> s.getBuildTools().name().toLowerCase(), Function.identity())));

        cover = generatorConfig.getProject().getCover();
        buildTool = generatorConfig.getBuildTool();
        projectProperties = generatorConfig.getProject();
        schemaTyps = Arrays.stream(SchemaType.values()).map(v -> v.name()).collect(Collectors.toList());
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
    public void generate(GeneratorConfig generatorConfig) throws Exception {
        // 初始化
        init(generatorConfig);
        // 创建根目录
        Path rootPath = projectProperties.getProjectRootPath();
        Files.createDirectories(rootPath);
        generatorConfig.getSchemas().forEach(s -> {
            List<SchemaMetadata> schemaDatas = readerMap.get(s.getTyp()).read(s);
            if (schemaDatas != null) {
                for (String pattern : s.getPatterns().split(",")) {
                    schemaDatas.forEach(d -> {
                        d.setPattern(pattern);
                        // 创建项目模块
                        buildModule(rootPath, generatorConfig.getProject().getPom(), d);
                        pomBuildOnce = false;
                    });
                }
            }
        });
        DEPS.remove();
    }


    public abstract void buildModule(Path path, PomProperties pomProperties, SchemaMetadata schemaData);

}
