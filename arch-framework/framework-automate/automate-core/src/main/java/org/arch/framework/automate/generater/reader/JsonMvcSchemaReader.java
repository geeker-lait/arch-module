package org.arch.framework.automate.generater.reader;

import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.AbstractGenerator;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaProperties;
import org.arch.framework.automate.generater.core.SourceName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:06 AM
 */
public class JsonMvcSchemaReader extends AbstractSchemaReader implements SchemaReadable {
    @Override
    public SourceName getSource() {
        return SourceName.JSON_SOURCE;
    }
    @Override
    public String getReaderName() {
        return this.getClass().getSimpleName();
    }
    @Override
    public List<DatabaseProperties> read(SchemaProperties source) {
        return null;
    }

    @Override
    public void read(AbstractGenerator abstractGenerator, GeneratorConfig generatorConfig) {

    }
}
