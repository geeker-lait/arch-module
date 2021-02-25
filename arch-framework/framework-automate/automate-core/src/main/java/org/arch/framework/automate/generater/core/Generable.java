package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.config.GeneratorConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public interface Generable {

    String MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
    String MAIN_RESOURCES = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    String TEST_JAVA = "src" + File.separator + "test" + File.separator + "java" + File.separator;
    String TEST_RESOURCES = "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    List<String> srcDirectorys = Arrays.asList(MAIN_JAVA, MAIN_RESOURCES, TEST_JAVA, TEST_RESOURCES);
    /**
     * 根据GeneratorConfig 构建项目
     *
     * @param generatorConfig
     * @throws IOException
     */
    void generate(GeneratorConfig generatorConfig) throws Exception;
}
