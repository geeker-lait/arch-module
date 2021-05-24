package org.arch.framework.automate.generater.reader;

import org.arch.framework.automate.common.module.Project;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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
            String[] resources = schemaProperties.getResources().split(",");
            ConcurrentHashMap<String, Project> resourceProjectMap = null;
            for (String resource : resources) {
                if (pattern == SchemaPattern.MVC) {
                    ReaderConfiguration configuration = buildConvertConfiguration(resource, schemaProperties, SchemaPattern.MVC);
                    // 设置缓存
                    if (nonNull(resourceProjectMap)) {
                    	configuration.setResourceProjectMap(resourceProjectMap);
                    }
                    schemaDatas.addAll(readMvc(configuration));
                    // 如果 readMvc(configuration) -> XmindUtils.getProject(configuration) -> 缓存(resourceProjectMap)
                    if (nonNull(configuration.getResourceProjectMap())) {
                        resourceProjectMap = configuration.getResourceProjectMap();
                    }
                }
                if (pattern == SchemaPattern.API) {
                    // 我们不是真的渴望旅行，我们不是真正渴望看山、看水、看风景，只是这灵魂，被城市束缚，捆绑太久，我们需要找回最本真的自己，这大概便是旅行的意义
                    // 设置缓存
                    ReaderConfiguration configuration = buildConvertConfiguration(resource, schemaProperties, SchemaPattern.API);
                    if (nonNull(resourceProjectMap)) {
                        configuration.setResourceProjectMap(resourceProjectMap);
                    }
                    schemaDatas.addAll(readApi(configuration));
                    // 如果 readApi(configuration) -> XmindUtils.getProject(configuration) -> 缓存(resourceProjectMap)
                    if (nonNull(configuration.getResourceProjectMap())) {
                        resourceProjectMap = configuration.getResourceProjectMap();
                    }
                }
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
    protected abstract List<? extends SchemaData> readMvc(ReaderConfiguration<T> readerConfiguration);


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
