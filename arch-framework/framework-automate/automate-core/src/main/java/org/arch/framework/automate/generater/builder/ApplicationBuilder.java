package org.arch.framework.automate.generater.builder;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.arch.framework.automate.generater.core.Buildable;
import org.arch.framework.automate.generater.core.TemplateName;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ApplicationBuilder extends AbstractBuilder implements Buildable {


    @Override
    public TemplateName getTemplateName() {
        return TemplateName.APPLICATION;
    }

    @Override
    public Map<String, Object> buildData(String fileName, Path filePath, PackageProperties packageProperties, TableProperties tableProperties) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.putAll(JSONUtil.parseObj(tableProperties));
        dataMap.putAll(JSONUtil.parseObj(packageProperties));
        dataMap.put("package",buildPkg(filePath));
        dataMap.put("mainClass",fileName.substring(0,fileName.lastIndexOf(".")));
        return dataMap;
    }
}
