package org.arch.framework.automate.generater.reader;

import org.arch.framework.automate.generater.core.ReaderConfiguration;
import org.arch.framework.automate.generater.core.SchemaConfiguration;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.ex.GeneratorCode;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.beans.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 2:27 PM
 */

public abstract class AbstractSchemaReader<T extends SchemaConfiguration> {


    protected List<SchemaPattern> getPatterns(SchemaProperties schemaProperties) {
        Assert.isEmpty(GeneratorCode.GENERATOR_CONFIG_ERROR, schemaProperties.getResources());
        Assert.isEmpty(GeneratorCode.GENERATOR_CONFIG_ERROR, schemaProperties.getPatterns());
        List<SchemaPattern> patterns = Arrays.stream(schemaProperties.getPatterns().split(","))
                //.filter(p -> Arrays.stream(SchemaPattern.values()).collect(Collectors.toSet()).contains(p.toUpperCase()))
                .map(p -> SchemaPattern.valueOf(p.toUpperCase())).collect(Collectors.toList());
        return patterns;
    }

    /**
     * 公共读取
     *
     * @param schemaProperties
     * @return
     */
    public List<SchemaData> doRead(SchemaProperties schemaProperties) {
        List<SchemaData> schemaDatas = new ArrayList<>();
        getPatterns(schemaProperties).forEach(pattern -> {
            /**
             * excel/database 文档只能匹配一个,不能同时匹配，即要么api 要么 mvc， xmind 支持同时匹配
             */
            if (pattern == SchemaPattern.MVC) {
                Arrays.stream(schemaProperties.getResources().split(",")).forEach(res -> {
                    ReaderConfiguration configuration = buildConvertConfiguration(res, schemaProperties, SchemaPattern.MVC);
                    schemaDatas.addAll(readDatabse(configuration));
                });
            }
            if (pattern == SchemaPattern.API) {
                // 我们不是真的渴望旅行，我们不是真正渴望看山、看水、看风景，只是这灵魂，被城市束缚，捆绑太久，我们需要找回最本真的自己，这大概便是旅行的意义
                Arrays.stream(schemaProperties.getResources().split(",")).forEach(res -> {
                    ReaderConfiguration configuration = buildConvertConfiguration(res, schemaProperties, SchemaPattern.API);
                    schemaDatas.addAll(readApi(configuration));
                });
            }
        });
        return schemaDatas;
    }

    /**
     * 模板方法，读取MVC
     *
     * @param readerConfiguration
     * @return
     */
    protected abstract List<? extends SchemaData> readDatabse(ReaderConfiguration<T> readerConfiguration);


    /**
     * 模板方法，读取API
     *
     * @param readerConfiguration
     * @return
     */
    protected abstract List<? extends SchemaData> readApi(ReaderConfiguration<T> readerConfiguration);

    /**
     * @param resName
     * @param schemaProperties
     * @param schemaPattern
     * @return
     */
    protected abstract ReaderConfiguration<T> buildConvertConfiguration(String resName, SchemaProperties schemaProperties, SchemaPattern schemaPattern);

}
