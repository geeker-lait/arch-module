package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.automate.common.enums.BuildToolsName;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service
public class GradleGenerator extends AbstractGenerator {

    @Override
    public BuildToolsName getBuildTools() {
        return BuildToolsName.GRADLE;
    }


    @Override
    public void buildModule(Path path, PomProperties pomProperties, List<SchemaData> schemaDatas) {

    }
}
