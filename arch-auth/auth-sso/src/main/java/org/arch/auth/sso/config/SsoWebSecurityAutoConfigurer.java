package org.arch.auth.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.common.config.SecurityCoreAutoConfigurer;

/**
 * 添加 UMS WebSecurityAutoConfigurer 核心配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.16 23:54
 */
@Configuration
public class SsoWebSecurityAutoConfigurer  {
    @Bean
    public SecurityCoreAutoConfigurer securityCoreAutoConfigurer() {
        return new SecurityCoreAutoConfigurer();
    }
}