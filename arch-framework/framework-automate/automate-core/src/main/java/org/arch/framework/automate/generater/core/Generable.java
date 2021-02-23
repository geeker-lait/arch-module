package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.config.GeneratorConfig;

import java.io.IOException;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public interface Generable {
    /**
     * 根据GeneratorConfig 构建项目
     *
     * @param generatorConfig
     * @throws IOException
     */
    void generate(GeneratorConfig generatorConfig) throws IOException;
}
