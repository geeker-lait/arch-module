package org.arch.framework.generater.core;

import lombok.extern.slf4j.Slf4j;

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
public abstract class AbstractFtlProcessor implements FtlProcessor {


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
