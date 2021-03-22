package org.arch.framework.automate.generater.reader;

import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.ex.GeneratorCode;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.beans.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 2:27 PM
 */

public abstract class AbstractSchemaReader {

    /**
     * 公共读取
     *
     * @param schemaProperties
     * @return
     */
    public List<SchemaMetadata> read(SchemaProperties schemaProperties) {
        Assert.isEmpty(GeneratorCode.GENERATOR_CONFIG_ERROR, schemaProperties.getResources());
        Assert.isEmpty(GeneratorCode.GENERATOR_CONFIG_ERROR, schemaProperties.getPatterns());

        List<SchemaMetadata> schemaMetadatas = new ArrayList<>();
        for (String pattern : schemaProperties.getPatterns().split(",")) {
            /**
             * excel/database 文档只能匹配一个,不能同时匹配，即要么api 要么 mvc
             */
            if (pattern.equalsIgnoreCase(SchemaPattern.MVC.name())) {
                Arrays.stream(schemaProperties.getResources().split(",")).forEach(res -> {
                    schemaMetadatas.addAll(readMvc(res, schemaProperties.getConfiguration()));
                });
            } else if (pattern.equalsIgnoreCase(SchemaPattern.API.name())) {
                // 我们不是真的渴望旅行，我们不是真正渴望看山、看水、看风景，只是这灵魂，被城市束缚，捆绑太久，我们需要找回最本真的自己，这大概便是旅行的意义
                Arrays.stream(schemaProperties.getResources().split(",")).forEach(res -> {
                    schemaMetadatas.addAll(readApi(res, schemaProperties.getConfiguration()));
                });
            } else {
                // 目前还没有支持其他schema
            }
        }
        return schemaMetadatas;
    }

    /**
     * 模板方法，读取MVC
     *
     * @param res
     * @param heads
     * @return
     */
    protected abstract List<SchemaMetadata> readMvc(String res, Map<String, String> heads);


    /**
     * 模板方法，读取API
     *
     * @param res
     * @param heads
     * @return
     */
    protected abstract List<SchemaMetadata> readApi(String res, Map<String, String> heads);


}
