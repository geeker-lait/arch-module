package org.arch.auth.sdk.config;

import org.arch.framework.mvc.resolver.TokenInfoHandlerMethodArgumentResolver;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * springMvc {@link TokenInfo} argument resolver
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.30 22:17
 */
@Configuration
public class TokenInfoWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenInfoHandlerMethodArgumentResolver());
    }
}
