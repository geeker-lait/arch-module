package org.arch.auth.jwt.config;

import org.arch.auth.jwt.service.ArchJwkSetUriConfig;
import org.arch.auth.jwt.service.ArchJwtAccountClaimsSetServiceImpl;
import org.arch.auth.jwt.service.ArchJwtCacheTransformServiceImpl;
import org.arch.auth.jwt.service.ArchJwtClaimTypeConverterSupplier;
import org.arch.auth.jwt.service.ArchJwtIdServiceImpl;
import org.arch.auth.jwt.service.ArchReAuthServiceImpl;
import org.arch.auth.jwt.service.SdkJwkEndpointPermissionServiceImpl;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import top.dcenter.ums.security.jwt.api.cache.service.JwtCacheTransformService;
import top.dcenter.ums.security.jwt.api.claims.service.CustomClaimsSetService;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkSetUriConfig;
import top.dcenter.ums.security.jwt.api.id.service.JwtIdService;
import top.dcenter.ums.security.jwt.api.supplier.JwtClaimTypeConverterSupplier;
import top.dcenter.ums.security.jwt.api.validator.service.ReAuthService;
import top.dcenter.ums.security.jwt.config.JwtAutoConfiguration;
import top.dcenter.ums.security.jwt.config.JwtAutoConfigurerAware;
import top.dcenter.ums.security.jwt.config.JwtPropertiesAutoConfiguration;
import top.dcenter.ums.security.jwt.config.JwtServiceAutoConfiguration;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

/**
 * jwt 服务自动配置.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.18 10:00
 */
@Configuration
@AutoConfigureAfter({JwtPropertiesAutoConfiguration.class})
@AutoConfigureBefore({JwtAutoConfiguration.class, JwtAutoConfigurerAware.class, JwtServiceAutoConfiguration.class})
public class ArchJwtServiceAutoConfiguration {

    @Bean
    public JwkSetUriConfig jwkSetUriConfig(AppProperties appProperties) {
        return new ArchJwkSetUriConfig(appProperties);
    }

    @Bean
    public CustomClaimsSetService customClaimsSetService(JwtProperties jwtProperties) {
        return new ArchJwtAccountClaimsSetServiceImpl(jwtProperties);
    }

    @Bean
    public JwtCacheTransformService<TokenInfo> jwtCacheTransformService(JwtProperties jwtProperties,
                                                                        @Qualifier("jwtTokenRedisSerializer")
                                                                        RedisSerializer<TokenInfo> redisSerializer) {
        return new ArchJwtCacheTransformServiceImpl(jwtProperties, redisSerializer);
    }

    @Bean
    public JwtClaimTypeConverterSupplier jwtClaimTypeConverterSupplier() {
        return new ArchJwtClaimTypeConverterSupplier();
    }

    @Bean
    public JwtIdService jwtIdService(IdService idService) {
        return new ArchJwtIdServiceImpl(idService);
    }

    @Bean
    public ReAuthService reAuthService(JwtProperties jwtProperties) {
        return new ArchReAuthServiceImpl(jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean(type = "top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService")
    public JwkEndpointPermissionService jwkEndpointPermissionService() {
        return new SdkJwkEndpointPermissionServiceImpl();
    }

}
