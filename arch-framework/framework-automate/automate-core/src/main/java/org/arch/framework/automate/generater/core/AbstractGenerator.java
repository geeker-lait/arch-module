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

    protected final static Map<String, DocumentProperties> DOCUMENT_MAP = new HashMap<>();
    protected final static Map<String, Buildable> BUILDER_MAP = new HashMap<>();
    protected final static Map<String, SchemaReadable> READER_MAP = new HashMap<>();
    protected final static Map<String, Generable> BUILD_TOOLS_MAP = new HashMap<>();
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


        DOCUMENT_MAP.putAll(generatorConfig.getDocuments().stream().collect(Collectors.toMap(DocumentProperties::getType, Function.identity())));
        BUILDER_MAP.putAll(builders.stream().collect(Collectors.toMap(b -> b.getTemplateName().getTemplate(), Function.identity())));
        READER_MAP.putAll(schemaReadables.stream().collect(Collectors.toMap(s -> s.getTyp().name().toLowerCase(), Function.identity())));
        BUILD_TOOLS_MAP.putAll(generables.stream().collect(Collectors.toMap(s -> s.getBuildTools().name().toLowerCase(), Function.identity())));

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
            SchemaReadable readable = READER_MAP.get(s.getTyp());
            if (readable != null) {
                List<SchemaData> schemaDatas = readable.read(s);
                if (schemaDatas != null) {
                    schemaDatas.forEach(sd -> {
                        // 创建项目模块
                        buildModule(rootPath, generatorConfig.getProject().getPom(), sd);
                        pomBuildOnce = false;
                    });
                }
            }
        });
        DEPS.remove();
    }


    public abstract void buildModule(Path path, PomProperties pomProperties, SchemaData schemaData);

}
