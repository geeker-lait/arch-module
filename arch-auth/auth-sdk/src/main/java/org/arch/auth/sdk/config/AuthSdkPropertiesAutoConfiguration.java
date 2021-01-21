package org.arch.auth.sdk.config;

import org.arch.auth.sdk.properties.CsrfProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * auth-sdk 属性自动设置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.21 13:22
 */
@Configuration
@Order(98)
@EnableConfigurationProperties({CsrfProperties.class})
public class AuthSdkPropertiesAutoConfiguration {
}
