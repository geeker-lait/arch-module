package org.arch.framework.automate.generater.reader;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.AbstractGenerator;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.ConfigProperties;
import org.arch.framework.automate.generater.core.SourceName;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:06 AM
 */
@Slf4j
@Service
public class XmindSchemaReader extends AbstractSchemaReader implements SchemaReadable {
    @Override
    public SourceName getSource() {
        return SourceName.XMIND_SOURCE;
    }
    @Override
    public String getReaderName() {
        return this.getClass().getSimpleName();
    }
    @Override
    public List<DatabaseProperties> read(ConfigProperties source) {
        return null;
    }

    @Override
    public void read(AbstractGenerator abstractGenerator, GeneratorConfig generatorConfig) {
        log.info("xmind reader reading ",generatorConfig.getSchemas().getXminds());
    }

    @Override
    public void read(AbstractGenerator abstractGenerator) {

    }
}
