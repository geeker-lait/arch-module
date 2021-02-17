package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.render.RenderingRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
@Slf4j
public abstract class AbstractProcessor implements TemplateProcessor {

    protected final static String MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
    protected final static String MAIN_RESOURCES = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    protected final static String TEST_JAVA = "src" + File.separator + "test" + File.separator + "java" + File.separator;
    protected final static String TEST_RESOURCES = "src" + File.separator + "test" + File.separator + "resources" + File.separator;

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
    protected Map<String, Path> creatMavenDirectory(RenderingRequest renderingRequest) {
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

        pathMap.put("rootPath",rootPath);
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
    protected void saveToFile(String code, String filePath, String fileName, boolean cover) throws IOException {
        String finalFileName = filePath + File.separator + fileName;
        // 这里有一个bug mac/linux 下没有盘符的问题,这里需要处理一下
        //new File(new File(“C:/a/b.txt”).getPath().substring(1)).toPath(), “c/a/b.txt”
        Path path = Paths.get(finalFileName);
        if (Files.exists(path)) {
            // 是否覆盖
            if (!cover) {
                log.info("skip {} due to file exists.", fileName);
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
        log.info("file path: {}", path);
    }
}
