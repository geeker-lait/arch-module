package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.properties.DatabaseProperties;

import java.io.IOException;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:07 AM
 */
public interface SchemaReadable<S extends ConfigProperties> {
    /**
     * 获取读取源
     *
     * @return
     */
    SourceName getSource();

    /**
     * 获取读取器到名称
     * @return
     */
    String getReaderName();

    /**
     * 读取
     *
     * @param source
     * @return
     */
    List<DatabaseProperties> read(S source) throws Exception;

    void read(AbstractGenerator abstractGenerator, GeneratorConfig generatorConfig) throws IOException;

    void read(AbstractGenerator abstractGenerator);
}
