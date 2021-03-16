package org.arch.framework.automate.generater.xmind;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.properties.XmindProperties;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 2:39 PM
 */
@Slf4j
@Service
public class XmindService {
    /**
     * 获取xmind中实体的定义
     * @param res
     * @param configuration
     * @return
     */
    public List<? extends SchemaMetadata> getEntityMetadate(String res, Map<String, String> configuration) {
        return null;
    }

    /**
     * 获取xmind中的api定义
     * @param res
     * @param configuration
     * @return
     */
    public List<? extends SchemaMetadata> getApiMetadate(String res, Map<String, String> configuration) {
        return null;
    }
}
