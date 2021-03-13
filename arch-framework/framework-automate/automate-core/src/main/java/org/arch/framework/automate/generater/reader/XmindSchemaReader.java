package org.arch.framework.automate.generater.reader;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.properties.XmindProoerties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:06 AM
 */
@Slf4j
@Service
public class XmindSchemaReader extends AbstractSchemaReader implements SchemaReadable<XmindProoerties> {

    @Override
    public SchemaType getTyp() {
        return SchemaType.XMIND;
    }

    @Override
    public List<XmindProoerties> read(SchemaProperties schemaProperties) {
        return null;
    }
}
