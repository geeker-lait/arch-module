package org.arch.framework.automate.generater.service;

import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.util.List;
import java.util.function.Function;

/**
 * @author lait.zhang@gmail.com
 * @description: 不同schema获取相关元数据 - 不同子类来实现
 *              使用Function 作为返回值（多个入参封装成一个实体即可）,主要是为了实现方法级别泛型作为入参，不用在类上添加泛型
 * @weixin PN15855012581
 * @date 3/24/2021 10:01 AM
 */
public interface SchemaService {

    /**
     * 获取表结构属性
     * @return
     */
    <T> Function<T, List<TableProperties>> getTableProperties();
    /**
     * 获取方法属性
     *
     * @return
     */
    <T> Function<T, List<MethodProperties>> getApiProperties();

    /**
     * 获取schemaMatedata
     * @return
     */
    <T> Function<T, List<SchemaMetadata>> getSchemaMatedatas();


}
