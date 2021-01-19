package org.arch.auth.sso.config;

import org.arch.auth.sso.properties.SsoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * arch sso 属性自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:16
 */
@Configuration
@Order(98)
@EnableConfigurationProperties({SsoProperties.class})
public class ArchPropertiesConfiguration {
}
