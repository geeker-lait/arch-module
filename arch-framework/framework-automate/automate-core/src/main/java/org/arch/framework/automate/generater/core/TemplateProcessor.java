package org.arch.framework.automate.generater.core;

import cn.hutool.db.meta.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.generater.config.PomProperties;
import org.arch.framework.automate.generater.render.RenderingRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author lait.zhang@gmail.com
 * @description: 模板文件处理
 * @weixin PN15855012581
 * @date 12/18/2020 8:02 PM
 */
public interface TemplateProcessor<T extends RenderingRequest> {

    String MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
    String MAIN_RESOURCES = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    String TEST_JAVA = "src" + File.separator + "test" + File.separator + "java" + File.separator;
    String TEST_RESOURCES = "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    /**
     * 获取文件
     *
     * @return
     */
    TemplateName getTemplate();


    /**
     * 模板处理器
     *
     * @param code
     * @param renderingRequest
     */
    void process(String code, T renderingRequest);




    /**
     * 创建模块
     *
     * @param renderingRequest
     */
    abstract void createModule(RenderingRequest renderingRequest);

    /**
     * 创建文件
     *
     * @param code
     * @param renderingRequest
     */
    abstract void createFile(String code, RenderingRequest renderingRequest);


    /**
     * 创建maven
     * 这里该用从yaml读取结构
     *
     * @param renderingRequest
     */
    default Map<String, Path> creatMavenDirectory(RenderingRequest renderingRequest) {
        Map<String, Path> pathMap = new HashMap<>();
        // 构架模块根目录
        Path rootPath = Paths.get(renderingRequest.getSavePath() + File.separator + renderingRequest.getModuleName());
        // 构建src/java
        Path srcJava = rootPath.resolve(MAIN_JAVA);
        // 构建src/resources
        Path srcResources = rootPath.resolve(MAIN_RESOURCES);
        // 构建test/java
        Path testJava = rootPath.resolve(TEST_JAVA);
        // 构建test/resources
        Path testResources = rootPath.resolve(TEST_RESOURCES);

        pathMap.put("rootPath", rootPath);
        pathMap.put("srcJava", srcJava);
        pathMap.put("srcResources", srcResources);
        pathMap.put("testJava", testJava);
        pathMap.put("testResources", testResources);

        pathMap.forEach((k, v) -> {
            try {
                Files.createDirectories(v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return pathMap;
    }

    /**
     * 保存文件
     *
     * @param code
     * @param filePath
     * @param fileName
     * @param cover
     * @throws IOException
     */
     default void saveToFile(String code, String filePath, String fileName, boolean cover) throws IOException {
        String finalFileName = filePath + File.separator + fileName;
        // 这里有一个bug mac/linux 下没有盘符的问题,这里需要处理一下
        //new File(new File(“C:/a/b.txt”).getPath().substring(1)).toPath(), “c/a/b.txt”
        Path path = Paths.get(finalFileName);
        if (Files.exists(path)) {
            // 是否覆盖
            if (!cover) {
                //log.info("skip {} due to file exists.", fileName);
                return;
            } else {
                Files.delete(path);
            }
        }

        Path dirPath = Paths.get(filePath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        Files.createFile(path);
        Files.write(path, code.getBytes());
        //log.info("file path: {}", path);
    }


    /**
     * @Author: lait.zhang@gmail.com
     * @Tel: 15801818092
     * @Date:9/10/2019
     * @Description: 创建文件
     */
    default void buildModule(PomModel pomModel, List<Table> tables) {
        String modulePath = buildFolder(pomModel);
        buildPom(pomModel, modulePath);
        if (pomModel.getProperties().getModule() == null) {
            return;
        } else if (ignoreModules.contains(pomModel.getProperties().getModule().toLowerCase())) {
            T dataModel = buildData(pomModel.getProperties(), null);
            buildFile(pomModel, dataModel);
        } else {
            if (tables != null) {
                tables.forEach(table -> {
                    T dataModel = buildData(pomModel.getProperties(), table);
                    buildFile(pomModel, dataModel);
                });
            }
        }

        System.out.println("Build >>>>>>>>>>>>>>>>>>>>>> " + pomModel.getArtifactId());
    }


    /**
     * @param pomModel
     * @Author: lait.zhang@gmail.com
     * @Tel: 15801818092
     * @Date:9/10/2019
     * @Description: 创建文件夹
     */
    default String buildFolder(PomProperties pomModel) {
        //String modulePath = pomModel.getPath();
        // 将com.unichain.project.entity 替换成com/unichain/project/entity
        String pkgPath = pomModel.getProperties().getPkg();
        if (StringUtils.isEmpty(pkgPath)) {
            pkgPath = pomModel.getProperties().getBasePkg().replaceAll("\\.", Matcher.quoteReplacement(File.separator));
            File file = new File(pkgPath);
            file.mkdirs();
        } else {
            pkgPath = pomModel.getProperties().getBasePkg().concat(File.separator + pkgPath).replaceAll("\\.", Matcher.quoteReplacement(File.separator));
            pkgPath = modulePath + JAVA_PATH + File.separator + pkgPath;
            File file = new File(pkgPath);
            file.mkdirs();
            file = new File(modulePath + RESOURCES_PATH);
            file.mkdirs();
            pomModel.setPath(pkgPath);
        }
        return modulePath;
    }


    default void buildPom(PomModel pomModel, String path) {
        // 生成POM文件
        if (StringUtils.isEmpty(pomModel.getArtifactId())) {
            pomModel.setArtifactId(pomModel.getParent().getArtifactId());
        }
        if (StringUtils.isEmpty(pomModel.getGroupId())) {
            pomModel.setGroupId(pomModel.getParent().getGroupId());
        }
        if (StringUtils.isEmpty(pomModel.getVersion())) {
            pomModel.setVersion(pomModel.getParent().getVersion());
        }
        if (StringUtils.isEmpty(path)) {
            path = pomModel.getPath();
        }
        try {
            //POM_TEMPLATE_PATH, POM_TEMPLATE, path, pomModel/
            Freemarker.printFile(POM_TEMPLATE_PATH, POM_TEMPLATE, pomModel, path + File.separator, "pom.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void wrapperDefaultValue(PropertiesModel propertiesModel, PropertiesModel baseModel) {
        // 设置包路径
        //baseModel.setPkg(propertiesModel.getPkgName());
        baseModel.setBasePkg(propertiesModel.getBasePkg());
//        if (null != baseModel.getImplementsClazz()) {
//            List<ClassModel> interfaceClass = new ArrayList<>();
//            propertiesModel.getImplementsClazz().forEach(s -> {
//                ClassModel classModel = new ClassModel();
//                //classModel.setName(s.substring(i+1));
//                //classModel.setPkg(s.substring(0,i));
//                interfaceClass.add(classModel);
//            });
//            baseModel.setImplementsClazz(interfaceClass);
//        }
//        if (null != propertiesModel.getExtendsClazz()) {
//            String classExtends = propertiesModel.getExtendsClazz().getName();
//            int i = classExtends.lastIndexOf(".");
//            ClassModel classModel = new ClassModel();
//            classModel.setName(classExtends.substring(i + 1));
//            classModel.setPkg(classExtends.substring(0, i));
//            baseModel.setExtendsClazz(classModel);
//        }

        if (StringUtils.isEmpty(baseModel.getPkg())) {
            baseModel.setPkg(propertiesModel.getModule().toLowerCase());
        }
        if (StringUtils.isEmpty(baseModel.getPrefix())) {
            baseModel.setPrefix(null == propertiesModel.getPrefix() ? "" : propertiesModel.getPrefix());
        }
        if (StringUtils.isEmpty(baseModel.getSuffix())) {
            baseModel.setSuffix(null == propertiesModel.getSuffix() ? "" : propertiesModel.getSuffix());
        }

    }


    default void genClassFile(PomModel pomModel, PropertiesModel dataModel) {
        PropertiesModel propertiesModel = pomModel.getProperties();
        String fileName = dataModel.getPrefix() + dataModel.getClassName() + dataModel.getSuffix();
        String extName = propertiesModel.getExtName();
        if (extName == null) {
            extName = "java";
        }
        String serviceFile = pomModel.getPath() + File.separator + fileName + "." + extName;
        String template = propertiesModel.getTemplate();
        if (template == null) {
            template = getTemplate();
        }
        int i = template.lastIndexOf("/");
        // 模板文件
        String ftlFile = template.substring(i + 1);
        String ftlPath = template.substring(0, i);
        try {
            Freemarker.printFile(ftlPath, ftlFile, serviceFile, dataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    default void genClassImplFile(PomModel pomModel, PropertiesModel dataModel, String templateFile) {
        PropertiesModel propertiesModel = pomModel.getProperties();
        String fileName = dataModel.getPrefix() + dataModel.getClassName() + dataModel.getSuffix() + "Impl";
        String template = propertiesModel.getTemplate();
        if (template == null) {
            template = getTemplate();
        }
        int i = template.lastIndexOf("/");
        String ftlPaht = template.substring(0, i);
        String extName = propertiesModel.getExtName();
        if (extName == null) {
            extName = "java";
        }
        String serviceImplJava = pomModel.getPath() + File.separator + "impl" + File.separator + fileName + "." + extName;
        try {
            Freemarker.printFile(ftlPaht, templateFile, serviceImplJava, dataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
