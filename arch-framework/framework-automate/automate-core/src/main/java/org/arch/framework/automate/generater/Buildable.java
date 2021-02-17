//package org.arch.framework.automate.generater;
//
//import cn.hutool.core.convert.Converter;
//import cn.hutool.db.meta.Table;
//import com.google.common.base.CaseFormat;
//import org.apache.commons.lang3.StringUtils;
//import org.arch.framework.automate.generater.config.DataProperties;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Matcher;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Date 9:53 PM 11/28/2018
// * @Param
// * @return
// * @Description //TODO
// */
//public interface Buildable<T extends DataProperties> {
//
//    // 下划线转驼峰大写, user_name -> UserName
//    Converter<String, String> UPPER_CAMEL_CONVERT = CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL);
//    // 下划线转驼峰小写, user_name -> userName
//    Converter<String, String> LOWER_UNDERSCORE_CONVERT = CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
//    // 下划线转驼峰小写, user_name -> userName
//    Converter<String, String> LOWER_CAMEL_CONVERT = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL);
//
//    String MAIN_PATH = File.separator + "src" + File.separator + "main" + File.separator;
//    String JAVA_PATH = MAIN_PATH + "java";
//    String RESOURCES_PATH = MAIN_PATH + "resources";
//
//    String JAVA_TEMPLATE_PATH = "templates/java/";
//    String POM_TEMPLATE_PATH = "templates/pom/";
//    String MAPPER_TEMPLATE_PATH = "templates/mapper/";
//
//    String ENTITY_TEMPLATE = "entity.ftl";
//    String MAPPER_TEMPLATE = "mapper.ftl";
//    String SERVICE_TEMPLATE = "service.ftl";
//    String SERVICE_IMPL_TEMPLATE = "serviceImpl.ftl";
//    String BIZ_TEMPLATE = "biz.ftl";
//    String BIZ_IMPL_TEMPLATE = "bizImpl.ftl";
//    String CONTROLLER_TEMPLATE = "controller.ftl";
//    String POM_TEMPLATE = "pom.ftl";
//
//    String FILE_EXT_JAVA = ".java";
//    String FILE_EXT_MAPPER = ".xml";
//
//    List<String> ignoreModules = Arrays.asList("application", "redis", "rabbitmq");
//
//    /**
//     * @Author: lait.zhang@gmail.com
//     * @Tel: 15801818092
//     * @Date:9/8/2019
//     * @Description: 创建数据
//     */
//    T buildData(PropertiesModel propertiesModel, Table table);
//
//    /**
//     * @Author: lait.zhang@gmail.com
//     * @Tel: 15801818092
//     * @Date:9/10/2019
//     * @Description: 创建文件
//     */
//    void buildFile(PomModel pomModel, T dataModel);
//
//
//    /**
//     * 获取具体模板
//     * @return
//     */
//    String getTemplate();
//    /**
//     * @Author: lait.zhang@gmail.com
//     * @Tel: 15801818092
//     * @Date:9/10/2019
//     * @Description: 创建文件
//     */
//    default void buildModule(PomModel pomModel, List<Table> tables) {
//        String modulePath = buildFolder(pomModel);
//        buildPom(pomModel, modulePath);
//        if (pomModel.getProperties().getModule() == null) {
//            return;
//        } else if (ignoreModules.contains(pomModel.getProperties().getModule().toLowerCase())) {
//            T dataModel = buildData(pomModel.getProperties(), null);
//            buildFile(pomModel, dataModel);
//        } else {
//            if (tables != null) {
//                tables.forEach(table -> {
//                    T dataModel = buildData(pomModel.getProperties(), table);
//                    buildFile(pomModel, dataModel);
//                });
//            }
//        }
//
//        System.out.println("Build >>>>>>>>>>>>>>>>>>>>>> " + pomModel.getArtifactId());
//    }
//
//
//    /**
//     * @param pomModel
//     * @Author: lait.zhang@gmail.com
//     * @Tel: 15801818092
//     * @Date:9/10/2019
//     * @Description: 创建文件夹
//     */
//    default String buildFolder(PomModel pomModel) {
//        String modulePath = pomModel.getPath();
//        // 将com.unichain.project.entity 替换成com/unichain/project/entity
//        String pkgPath = pomModel.getProperties().getPkg();
//        if (StringUtils.isEmpty(pkgPath)) {
//            pkgPath = pomModel.getProperties().getBasePkg().replaceAll("\\.", Matcher.quoteReplacement(File.separator));
//            File file = new File(pkgPath);
//            file.mkdirs();
//        } else {
//            pkgPath = pomModel.getProperties().getBasePkg().concat(File.separator + pkgPath).replaceAll("\\.", Matcher.quoteReplacement(File.separator));
//            pkgPath = modulePath + JAVA_PATH + File.separator + pkgPath;
//            File file = new File(pkgPath);
//            file.mkdirs();
//            file = new File(modulePath + RESOURCES_PATH);
//            file.mkdirs();
//            pomModel.setPath(pkgPath);
//        }
//        return modulePath;
//    }
//
//
//    default void buildPom(PomModel pomModel, String path) {
//        // 生成POM文件
//        if (StringUtils.isEmpty(pomModel.getArtifactId())) {
//            pomModel.setArtifactId(pomModel.getParent().getArtifactId());
//        }
//        if (StringUtils.isEmpty(pomModel.getGroupId())) {
//            pomModel.setGroupId(pomModel.getParent().getGroupId());
//        }
//        if (StringUtils.isEmpty(pomModel.getVersion())) {
//            pomModel.setVersion(pomModel.getParent().getVersion());
//        }
//        if (StringUtils.isEmpty(path)) {
//            path = pomModel.getPath();
//        }
//        try {
//            //POM_TEMPLATE_PATH, POM_TEMPLATE, path, pomModel/
//            Freemarker.printFile(POM_TEMPLATE_PATH, POM_TEMPLATE, pomModel, path + File.separator, "pom.xml");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    default void wrapperDefaultValue(PropertiesModel propertiesModel, PropertiesModel baseModel) {
//        // 设置包路径
//        //baseModel.setPkg(propertiesModel.getPkgName());
//        baseModel.setBasePkg(propertiesModel.getBasePkg());
////        if (null != baseModel.getImplementsClazz()) {
////            List<ClassModel> interfaceClass = new ArrayList<>();
////            propertiesModel.getImplementsClazz().forEach(s -> {
////                ClassModel classModel = new ClassModel();
////                //classModel.setName(s.substring(i+1));
////                //classModel.setPkg(s.substring(0,i));
////                interfaceClass.add(classModel);
////            });
////            baseModel.setImplementsClazz(interfaceClass);
////        }
////        if (null != propertiesModel.getExtendsClazz()) {
////            String classExtends = propertiesModel.getExtendsClazz().getName();
////            int i = classExtends.lastIndexOf(".");
////            ClassModel classModel = new ClassModel();
////            classModel.setName(classExtends.substring(i + 1));
////            classModel.setPkg(classExtends.substring(0, i));
////            baseModel.setExtendsClazz(classModel);
////        }
//
//        if(StringUtils.isEmpty(baseModel.getPkg())){
//            baseModel.setPkg(propertiesModel.getModule().toLowerCase());
//        }
//        if (StringUtils.isEmpty(baseModel.getPrefix())) {
//            baseModel.setPrefix(null == propertiesModel.getPrefix()?"":propertiesModel.getPrefix());
//        }
//        if (StringUtils.isEmpty(baseModel.getSuffix())) {
//            baseModel.setSuffix(null == propertiesModel.getSuffix()?"":propertiesModel.getSuffix());
//        }
//
//    }
//
//
//    default void genClassFile(PomModel pomModel, PropertiesModel dataModel) {
//        PropertiesModel propertiesModel = pomModel.getProperties();
//        String fileName = dataModel.getPrefix() + dataModel.getClassName() + dataModel.getSuffix();
//        String extName = propertiesModel.getExtName();
//        if(extName == null){
//            extName = "java";
//        }
//        String serviceFile = pomModel.getPath() + File.separator + fileName + "." + extName;
//        String template = propertiesModel.getTemplate();
//        if (template == null) {
//            template = getTemplate();
//        }
//        int i = template.lastIndexOf("/");
//        // 模板文件
//        String ftlFile = template.substring(i + 1);
//        String ftlPath = template.substring(0, i);
//        try {
//            Freemarker.printFile(ftlPath, ftlFile, serviceFile, dataModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    default void genClassImplFile(PomModel pomModel, PropertiesModel dataModel,String templateFile) {
//        PropertiesModel propertiesModel = pomModel.getProperties();
//        String fileName = dataModel.getPrefix() + dataModel.getClassName() + dataModel.getSuffix() + "Impl";
//        String template = propertiesModel.getTemplate();
//        if (template == null) {
//            template = getTemplate();
//        }
//        int i =template.lastIndexOf("/");
//        String ftlPaht = template.substring(0, i);
//        String extName = propertiesModel.getExtName();
//        if(extName == null){
//            extName = "java";
//        }
//        String serviceImplJava = pomModel.getPath() + File.separator + "impl" + File.separator + fileName + "." + extName;
//        try {
//            Freemarker.printFile(ftlPaht, templateFile, serviceImplJava, dataModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
