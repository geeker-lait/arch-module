package org.arch.framework.automate.generater.builder;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.HashMap;
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
    public Map<String, Object> buildData(String fileName, Path filePath, PackageProperties packageProperties, TableProperties tableProperties) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.putAll(JSONUtil.parseObj(tableProperties));
        dataMap.putAll(JSONUtil.parseObj(packageProperties));
        // 包
        dataMap.put("package", buildPkg(filePath));
        dataMap.put("","");
        dataMap.put("","");
        dataMap.put("","");
        return dataMap;
    }
}
