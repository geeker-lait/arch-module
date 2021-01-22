package org.arch.application.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 解决前端站点(主要为JavaScript发起的Ajax请求)访问的跨域问题
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private RedisTemplate redisTemplate;
    @Bean
    public FormContentFilter formContentFilter() {
        return new FormContentFilter();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/bootstrap/**");
        addInterceptor.excludePathPatterns("/img/**");
//        addInterceptor.excludePathPatterns("/swagger-ui.html");
//        addInterceptor.excludePathPatterns("/swagger**/**");
//        addInterceptor.excludePathPatterns("/swagger**");
//        addInterceptor.excludePathPatterns("/v2/**");
//        addInterceptor.excludePathPatterns("/webjars/**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private HandlerInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")    //允许所有前端站点调用
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(1728000);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        TokenResolver tokenResolver = new TokenResolver(redisTemplate);
//        resolvers.add(tokenResolver);
    }

}
