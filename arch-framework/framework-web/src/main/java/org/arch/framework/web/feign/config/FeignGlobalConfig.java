package org.arch.framework.web.feign.config;

import feign.Logger;
import org.arch.framework.web.feign.interceptor.FeignGlobalRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.time.Duration;

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
    /**
     * 授权服务器的时钟与资源服务器的时钟可能存在偏差, 设置时钟偏移量以消除不同服务器间的时钟偏差的影响, 通过属性 ums.jwt.clockSkew 设置.
     */
    private final Duration clockSkew;

    public FeignGlobalConfig(TenantContextHolder tenantContextHolder,
                             String tenantHeaderName, Duration clockSkew) {
        this.tenantContextHolder = tenantContextHolder;
        this.tenantHeaderName = tenantHeaderName;
        this.clockSkew = clockSkew;
    }

    @Profile("dev")
    @Bean
    public Logger.Level umsAccountDevLogger() {
        return Logger.Level.FULL;
    }

    @Profile("prod")
    @Bean
    public Logger.Level umsAccountProdLogger() {
        return Logger.Level.BASIC;
    }

    @Bean
    public FeignGlobalRequestInterceptor tokenRequestInterceptor() {
        return new FeignGlobalRequestInterceptor(tenantContextHolder, tenantHeaderName, clockSkew);
    }
}
