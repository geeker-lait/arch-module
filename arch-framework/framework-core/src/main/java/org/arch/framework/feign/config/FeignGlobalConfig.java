package org.arch.framework.feign.config;

import feign.Logger;
import feign.Retryer;
import org.arch.framework.feign.interceptor.TokenRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * feign 客户端全局配置
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:46
 */
public class FeignGlobalConfig {

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
    public Retryer retry() {
        // default Retryer will retry 5 times waiting waiting
        // 100 ms per retry with a 1.5* back off multiplier
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

    @Bean
    public TokenRequestInterceptor tokenRequestInterceptor(JwtProperties jwtProperties) {
        return new TokenRequestInterceptor(jwtProperties);
    }
}
