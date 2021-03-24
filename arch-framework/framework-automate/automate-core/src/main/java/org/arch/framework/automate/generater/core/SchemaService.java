package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/24/2021 10:01 AM
 */
public interface SchemaService {
    /**
     * 获取表结构属性
     *
     * @return
     */
    List<TableProperties> getTableProperties();

    /**
     * 获取方法属性
     *
     * @return
     */
    List<MethodProperties> getApiProperties();

    /**
     * 获取schemaMatedata
     * @return
     */
    List<SchemaMetadata> getSchemaMatedatas();


}
