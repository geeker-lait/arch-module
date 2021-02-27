package org.arch.framework.automate.generater.builder;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.Generable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/24/2021 5:11 PM
 */
@Slf4j
@Component
public class DockerBuilder extends AbstractBuilder implements Buildable {
    @Override
    public TemplateName getTemplateName() {
        return TemplateName.DOCKER;
    }


    @Override
    public void build(Path path, TemplateEngine templateEngine, ProjectProperties projectProperties, PackageProperties packageProperties, DatabaseProperties databaseProperties) throws IOException {
        String fileName = buildFileName(packageProperties, "Dockerfile", false);
        String ext = StringUtils.isEmpty(packageProperties.getExt()) ? "" : packageProperties.getExt();
        Path fileDir = path.resolve(Generable.MAIN_RESOURCES);
        Files.createDirectories(fileDir);
        Path filePath = Paths.get(fileDir.toString().concat(File.separator).concat(fileName).concat(ext));
        buildFile(projectProperties.getCover(),filePath);
        Map<String, Object> dataMap = buildData(projectProperties, packageProperties, null);
        // 增加docker文件特有的数据
        dataMap.put("","");
        // 获取模板并渲染
        String code = templateEngine.getTemplate(packageProperties.getTemplate()).render(dataMap);
        // 写入文件
        Files.write(filePath, code.getBytes());
    }

}
