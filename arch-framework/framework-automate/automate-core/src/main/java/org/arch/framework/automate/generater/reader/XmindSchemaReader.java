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
import org.springframework.beans.factory.annotation.Autowired;
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
    protected List<SchemaMetadata> readMvc(String res, Map<String, String> heads) {

        List<SchemaMetadata> schemaMetadata = new ArrayList<>();

        return schemaMetadata;
    }

    @Override
    protected List<SchemaMetadata> readApi(String res, Map<String, String> heads) {
        List<SchemaMetadata> schemaMetadata = new ArrayList<>();
        Map<String, String> tableMap = new HashMap<>();
        // 从类路劲加载
        if (-1 != res.indexOf("classpath:")) {
            res = new ClassPathResource(res.split(":")[1]).getAbsolutePath();
        }
        String destDirPath = res.concat("\\UnZip\\");
        try {
            File file = new File(res);
            UnZipUtil.unZip(file, destDirPath);
        } catch (Exception e) {
            log.error("解压xmind文件异常：res:{}", res, e);
        }
        metaDataService.parseMetaData(destDirPath.concat("\\content.json"));
        return schemaMetadata;
    }
}
