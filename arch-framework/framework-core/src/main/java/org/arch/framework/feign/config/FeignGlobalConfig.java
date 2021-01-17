package org.arch.framework.feign.config;

import feign.Logger;
import org.arch.framework.feign.interceptor.FeignGlobalRequestInterceptor;
import org.springframework.context.annotation.Bean;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

/**
 * feign 客户端全局配置
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:46
 */
public class FeignGlobalConfig {

    private final TenantContextHolder tenantContextHolder;
    private final String tenantHeaderName;

    public FeignGlobalConfig(TenantContextHolder tenantContextHolder,
                             String tenantHeaderName) {
        this.tenantContextHolder = tenantContextHolder;
        this.tenantHeaderName = tenantHeaderName;
    }

//    @Profile("dev")
    @Bean
    public Logger.Level umsAccountDevLogger() {
        return Logger.Level.FULL;
    }

//    @Profile("prod")
//    @Bean
//    public Logger.Level umsAccountProdLogger() {
//        return Logger.Level.BASIC;
//    }

    @Bean
    public FeignGlobalRequestInterceptor tokenRequestInterceptor() {
        return new FeignGlobalRequestInterceptor(tenantContextHolder, tenantHeaderName);
    }
}
