package org.arch.application.user.config;//package com.uni.skit.user.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
///**
// * 解决前端站点(主要为JavaScript发起的Ajax请求)访问的跨域问题
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")    //允许所有前端站点调用
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
//                .maxAge(1728000);
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        TokenResolver tokenResolver = new TokenResolver(redisTemplate);
//        resolvers.add(tokenResolver);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(new AuthInterceptor())
////                .addPathPatterns("/**")
////                .excludePathPatterns("/regist/**")
////                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
//
//    }
//}
