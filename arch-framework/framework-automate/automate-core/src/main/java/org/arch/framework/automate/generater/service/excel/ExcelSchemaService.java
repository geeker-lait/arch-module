package org.arch.framework.automate.generater.service.excel;

import org.arch.framework.automate.generater.core.AbstractSchemaService;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.service.SchemaService;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/24/2021 10:06 AM
 */
public class ExcelSchemaService extends AbstractSchemaService implements SchemaService {
    @Override
    public List<TableProperties> getTableProperties() {
        return null;
    }

    @Override
    public List<MethodProperties> getApiProperties() {
        return null;
    }

    @Override
    public List<SchemaMetadata> getSchemaMatedatas() {
        return null;
    }
}
