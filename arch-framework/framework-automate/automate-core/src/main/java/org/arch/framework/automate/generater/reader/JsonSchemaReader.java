package org.arch.framework.automate.generater.reader;

import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SourceName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:06 AM
 */
public class JsonSchemaReader extends AbstractSchemaReader implements SchemaReadable {
    @Override
    public SourceName getSource() {
        return SourceName.JSON_SOURCE;
    }
    @Override
    public List<DatabaseProperties> read(org.arch.framework.automate.generater.core.SchemaSource source) {
        return null;
    }
}
