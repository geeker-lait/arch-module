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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
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
public abstract class AbstractGenerator implements Generable, ApplicationContextAware {

    protected final static Map<String, DocumentProperties> packagePropertiesMap = new HashMap<>();
    protected final static Map<String, Buildable> builderMap = new HashMap<>();
    protected final static Map<String, SchemaReadable> schemaReaderMap = new HashMap<>();
    protected final static Map<String, Generable> buildToolMap = new HashMap<>();
    protected final static ThreadLocal<List<DependencieProterties>> DEPS = new ThreadLocal<>();

    protected TemplateEngine engine;
    protected ProjectProperties projectProperties;
    protected List<SchemaProperties> schemaProperties;
    protected Boolean cover;
    protected boolean pomBuildOnce = true;
    protected String buildTool;
    protected List<String> schemaTyps;
    //protected String source;


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



        packagePropertiesMap.putAll(generatorConfig.getDocuments().stream().collect(Collectors.toMap(DocumentProperties::getType, Function.identity())));
        builderMap.putAll(builders.stream().collect(Collectors.toMap(b -> b.getTemplateName().getTemplate(), Function.identity())));
        schemaReaderMap.putAll(schemaReadables.stream().collect(Collectors.toMap(s -> s.getSource().getSource(), Function.identity())));
        buildToolMap.putAll(generables.stream().collect(Collectors.toMap(s -> s.getBuildTools().name().toLowerCase(), Function.identity())));

        cover = generatorConfig.getProject().getCover();
        buildTool = generatorConfig.getBuildTool();
        //source = generatorConfig.getSource();

        projectProperties = generatorConfig.getProject();
        schemaProperties = generatorConfig.getSchemas();

        schemaTyps = Arrays.stream(SchemaType.values()).map(v->v.name()).collect(Collectors.toList());
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

        generatorConfig.getSchemas().forEach(s->{
            if(schemaTyps.contains(s.getTyp())){
                SchemaType.valueOf(s.getTyp());
            }
        });


//        ProjectProperties projectProperties = generatorConfig.getProject();
//        PomProperties pomProperties = projectProperties.getPom();
        //List<DatabaseProperties> databasePropertiesList;
        //String source = generatorConfig.getSource();
        // 确定生成源
//        if (StringUtils.isEmpty(source)) {
//            throw new CodegenException("must assign a source name,it can be database or excel or json");
//        }

//        schemaProperties.getExcels().stream().forEach(excelProperties -> {
//            String reader = excelProperties.getSchemaReader();
//
//
//
//        });
//        Arrays.stream(source.split(",")).forEach(s-> {
//            try {
//                schemaReaderMap.get(s).read(this,generatorConfig);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        // 多schema 读取
         //schemaReaderMap.get(source).read(this);
        // 默认支持database,如果是excel 转换为database
//        if (source.equalsIgnoreCase("database")) {
//            String databaseName = generatorConfig.getDatabase().getName();
//            if (databaseName == null) {
//                throw new CodegenException("database name is null");
//            }
//            databasePropertiesList = schemaReaderMap.get(source).read(generatorConfig.getDatabase());
//        } else if (source.equalsIgnoreCase("excel")) {
//            /*List<ExcelProperties> excelPropertiesLists = generatorConfig.getExcels();
//            excelPropertiesLists.forEach(e->{
//                log.info(" 处理不同类型的Excel {}",e.getSchemaReader());
//            });*/
//            ExcelProperties excelProperties = generatorConfig.getExcel();
//            if (StringUtils.isEmpty(excelProperties.getFile())) {
//                throw new CodegenException("excel file is null");
//            }
//            databasePropertiesList = schemaReaderMap.get(source).read(excelProperties);
//        } else if (source.equalsIgnoreCase("json")) {
//            databasePropertiesList = schemaReaderMap.get(source).read(generatorConfig.getJson());
//        } else {
//            throw new CodegenException("not support source");
//        }
        // 创建根目录
//        Path rootPath = projectProperties.getProjectRootPath();
//        Files.createDirectories(rootPath);
        // 创建项目
//        for (DatabaseProperties d : databasePropertiesList) {
//            // 创建模块
//            buildModule(rootPath, /*projectProperties,*/ pomProperties/*, d*/);
//            pomBuildOnce = false;
//        }
        DEPS.remove();
    }


    public abstract void buildModule(Path path, /*ProjectProperties projectProperties,*/ PomProperties pomProperties/*, DatabaseProperties databaseProperties*/) throws IOException;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {


        applicationContext.getBeansOfType(Buildable.class);

//        packagePropertiesMap.putAll(generatorConfig.getDocuments().stream().collect(Collectors.toMap(DocumentProperties::getType, Function.identity())));
//        builderMap.putAll(builders.stream().collect(Collectors.toMap(b -> b.getTemplateName().getTemplate(), Function.identity())));
//        schemaReaderMap.putAll(schemaReadables.stream().collect(Collectors.toMap(s -> s.getSource().getSource(), Function.identity())));
//        buildToolMap.putAll(generables.stream().collect(Collectors.toMap(s -> s.getBuildTools().name().toLowerCase(), Function.identity())));

    }
}
