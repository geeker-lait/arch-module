package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.PomProperties;
import org.arch.framework.automate.generater.properties.ProjectProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

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
    public void buildModule(Path path, ProjectProperties projectProperties, PomProperties pomProperties, PomProperties pomPropertiesPatent, DatabaseProperties databaseProperties) throws IOException {
        log.info("gradle is  null implementor");
    }
}
