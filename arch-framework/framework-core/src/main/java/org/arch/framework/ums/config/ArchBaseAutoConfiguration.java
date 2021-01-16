package org.arch.framework.ums.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.framework.ums.tenant.context.handler.ArchTenantContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 框架必要的自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 16:40
 */
@Configuration
@AutoConfigureAfter({AppPropertiesAutoConfiguration.class})
public class ArchBaseAutoConfiguration {

    @Bean
    public ArchTenantContextHolder archTenantContextHolder(AppProperties appProperties) {
        return new ArchTenantContextHolder(appProperties);
    }
}
