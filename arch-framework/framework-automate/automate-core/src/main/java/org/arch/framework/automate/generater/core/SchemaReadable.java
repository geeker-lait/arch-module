package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.properties.DatabaseProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:07 AM
 */
public interface SchemaReadable<S extends SchemaSource> {
    /**
     * 获取读取源
     *
     * @return
     */
    SourceName getSource();

    /**
     * 读取
     *
     * @param source
     * @return
     */
    List<DatabaseProperties> read(S source) throws Exception;
}
