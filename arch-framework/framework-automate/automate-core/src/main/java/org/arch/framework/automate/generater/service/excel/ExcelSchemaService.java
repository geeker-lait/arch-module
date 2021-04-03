package org.arch.framework.automate.generater.service.excel;

import org.arch.framework.automate.generater.core.AbstractSchemaService;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.service.SchemaService;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.util.List;
import java.util.function.Function;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/24/2021 10:06 AM
 */
public class ExcelSchemaService extends AbstractSchemaService implements SchemaService {


    /**
     * 获取xmind中实体/表的定义
     *  todo 空实现为了不报错添加<T> 实现后可以去除 <T> 参考 org.arch.framework.automate.generater.service.database.DatabaseSchemaService#getTableProperties()
     * @return
     */
    @Override
    public <T> Function<T, List<TableProperties>> getTableProperties() {
        return null;
    }

    @Override
    public <T> Function<T, List<MethodProperties>> getApiProperties() {
        return null;
    }

    @Override
    public <T> Function<T,  List<SchemaMetadata>> getSchemaMatedatas() {
        return null;
    }
}
