package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.render.RenderingRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
@Slf4j
public abstract class AbstractProcessor implements TemplateProcessor {

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
     * @param renderingRequest
     */
    protected void creatMavenDirectory(RenderingRequest renderingRequest) {
        Path dirPath = Paths.get(renderingRequest.getSavePath() + File.separator + renderingRequest.getModuleName());
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String finalFileName = filePath + fileName;
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
