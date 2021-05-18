package org.arch.framework.ums.config;

import org.arch.framework.ums.properties.AppProperties;
import org.arch.framework.ums.properties.AuthClientScopesCacheProperties;
import org.arch.framework.ums.properties.EncryptProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * arch app 属性自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:16
 */
@Configuration
@Order(98)
@EnableConfigurationProperties({AppProperties.class, AuthClientScopesCacheProperties.class, EncryptProperties.class})
public class AppPropertiesAutoConfiguration {
}
