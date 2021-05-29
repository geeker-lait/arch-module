package org.arch.automate.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.22 16:04
 */
@Configuration
@EnableConfigurationProperties({GeneratorConfig.class})
public class GeneratorConfigAutoConfiguration {
}
