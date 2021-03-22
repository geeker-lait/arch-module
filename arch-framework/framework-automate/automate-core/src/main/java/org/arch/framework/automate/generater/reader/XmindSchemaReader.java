package org.arch.framework.automate.generater.reader;

import cn.hutool.core.io.resource.ClassPathResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.xmind.UnZipUtil;
import org.arch.framework.automate.generater.xmind.XmindService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    protected List<SchemaMetadata> readMvc(String res, Map<String, String> configuration) {
        List<SchemaMetadata> schemaMetadatas = new ArrayList<>();
        List<? extends SchemaMetadata> schemaMetadata = xmindService.getEntityMetadate(res, configuration);
        schemaMetadatas.addAll(schemaMetadata);
        return schemaMetadatas;
    }

    @Override
    protected List<SchemaMetadata> readApi(String res, Map<String, String> configuration) {
        List<SchemaMetadata> schemaMetadatas = new ArrayList<>();
        schemaMetadatas.addAll(xmindService.getApiMetadate(res, configuration));
        return schemaMetadatas;
    }


}
