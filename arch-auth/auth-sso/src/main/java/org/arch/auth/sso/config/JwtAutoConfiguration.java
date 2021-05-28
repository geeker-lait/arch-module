package org.arch.auth.sso.config;

import org.arch.framework.ums.jwt.bearer.ArchBearerTokenAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.dcenter.ums.security.jwt.config.JwtPropertiesAutoConfiguration;
import top.dcenter.ums.security.jwt.properties.BearerTokenProperties;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.14 22:46
 */
@Configuration
@AutoConfigureAfter({JwtPropertiesAutoConfiguration.class})
public class JwtAutoConfiguration {
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(JwtProperties jwtProperties) {
        BearerTokenProperties bearer = jwtProperties.getBearer();
        return new ArchBearerTokenAuthenticationEntryPoint(bearer.getBearerTokenHeaderName(),
                bearer.getBearerTokenParameterName());
    }
}
