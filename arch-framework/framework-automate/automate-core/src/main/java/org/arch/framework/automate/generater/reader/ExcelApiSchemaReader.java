package org.arch.framework.automate.generater.reader;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.AbstractGenerator;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SourceName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.ExcelProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Component
public class ExcelApiSchemaReader extends AbstractSchemaReader implements SchemaReadable<ExcelProperties> {
    @Override
    public SourceName getSource() {
        return SourceName.EXCEl_SOURCE;
    }

    @Override
    public String getReaderName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public List<DatabaseProperties> read(ExcelProperties source) throws Exception {
        return null;
    }

    @Override
    public void read(AbstractGenerator abstractGenerator, GeneratorConfig generatorConfig) {

    }
}
