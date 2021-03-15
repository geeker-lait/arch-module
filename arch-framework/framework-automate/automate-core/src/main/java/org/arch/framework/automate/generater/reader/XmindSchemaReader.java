package org.arch.framework.automate.generater.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.properties.XmindProoerties;
import org.arch.framework.automate.generater.xmind.XmindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:06 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class XmindSchemaReader extends AbstractSchemaReader implements SchemaReadable {

    private final XmindService xmindService;

    @Override
    public SchemaType getTyp() {
        return SchemaType.XMIND;
    }


    @Override
    public List<SchemaMetadata> read(SchemaProperties schemaProperties) {
        // 如果有有特殊处理，再次处理，如果没有则调用父类通用处理
        return super.read(schemaProperties);
    }

    @Override
    protected List<SchemaMetadata> readMvc(String excel, Map<String, String> heads) {

        List<SchemaMetadata> schemaMetadata = new ArrayList<>();

        return schemaMetadata;
    }

    @Override
    protected List<SchemaMetadata> readApi(String excel, Map<String, String> heads) {
        List<SchemaMetadata> schemaMetadata = new ArrayList<>();

        return schemaMetadata;
    }
}
