package org.arch.application.admin.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Author lait.zhang@gmail.com
 * Description 请求时对参数做一些处理
 */
public class TokenResolver extends HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {
//    final static String TOKEN = "Authorization";
//    private RedisTemplate<String, Token> redisTemplate;
//
//    public TokenResolver(RedisTemplate tokenClient) {
//        this.redisTemplate = tokenClient;
//    }
//
//    @Override
//    public boolean supportsParameter(MethodParameter methodParameter) {
//        //return true;
//        return methodParameter.getParameterType().equals(Token.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
//                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        String stoken = nativeWebRequest.getHeader(TOKEN);
//        Token token = redisTemplate.opsForValue().get(stoken);
//        if (token == null) {
//            throw new Exception();
//        }
//        return token;
//    }
}
