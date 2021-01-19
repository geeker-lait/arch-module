package org.arch.auth.sdk.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Properties;

/**
 * 实现 YAML 的“额外”配置文件加载. <br>
 * {@code copy of https://www.cnblogs.com/jinyuanya/p/13974594.html}
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.17 21:11
 */
public class MixPropertySourceFactory extends DefaultPropertySourceFactory {

    /**
     * 改造实现YAML的“额外”配置文件加载。
     *
     * @param name      我们资源文件的名字
     * @param resource  编码资源的一个对象
     * @return          返回 {@link PropertySource}
     * @throws IOException  读取文件异常
     */
    @Override
    @NonNull
    public PropertySource<?> createPropertySource(@Nullable String name, @NonNull EncodedResource resource) throws IOException {

        String sourceName = name != null ? name : resource.getResource().getFilename();

        //如果是 yml 格式配置文件就先转成 properties 文件格式再走父类方法就好了。
        //noinspection AlibabaAvoidComplexCondition,AlibabaUndefineMagicConstant
        if (sourceName != null &&(sourceName.endsWith(".yml") || sourceName.endsWith(".yaml"))) {
            Properties propertiesFromYaml = loadYml(resource);
            //将YML配置转成 Properties 之后，再用 PropertiesPropertySource 绑定
            return new PropertiesPropertySource(sourceName, propertiesFromYaml);
        } else {
            //如果是 Properties 文件就默认走父类方法处理就好了。
            return super.createPropertySource(name, resource);
        }
    }

    /**
     * 将 YML 格式的配置转成 Properties 配置
     */
    private Properties loadYml(EncodedResource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

}