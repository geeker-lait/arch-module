package org.arch.framework.automate.generater.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.arch.framework.automate.generater.properties.XmindProperties;
import org.arch.framework.automate.generater.service.xmind.XmindSchemaService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    private final XmindSchemaService xmindSchemaService;

    @Override
    public SchemaType getTyp() {
        return SchemaType.XMIND;
    }

    @Override
    public List<SchemaMetadata> read(SchemaProperties schemaProperties) {
        // 如果有有特殊处理，再次处理，如果没有则调用父类通用处理
        return super.doRead(schemaProperties);
    }

    @Override
    protected List<SchemaMetadata> readMvc(String res, Map<String, String> configuration) {
        // todo getTableProperties.apply 入参需要构建，暂时只用 res(为了不报错)     getTableProperties方法暂时是空实现
        List<TableProperties> schemaMetadata = xmindSchemaService.getTableProperties().apply(res);
        XmindProperties xmindProperties = new XmindProperties();
        xmindProperties.setPattern(SchemaPattern.MVC.getPattern());
        xmindProperties.setTables(schemaMetadata);
        return Arrays.asList(xmindProperties);
    }

    @Override
    protected List<SchemaMetadata> readApi(String res, Map<String, String> configuration) {
        // todo  getApiProperties.apply 入参需要构建，暂时只用 res(为了不报错)  getApiProperties暂时是空实现
        List<MethodProperties> methodProperties = xmindSchemaService.getApiProperties().apply(res);
        XmindProperties xmindProperties = new XmindProperties();
        xmindProperties.setPattern(SchemaPattern.API.getPattern());
        xmindProperties.setApis(methodProperties);
        //schemaMetadatas.addAll(xmindSchemaService.getApiMetadate(res, configuration));
        return  Arrays.asList(xmindProperties);
    }


}
