package org.arch.framework.mvc.resolver;

import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * SpringMVC {@link TokenInfo} argument resolver
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.30 10:22
 */
public class TokenInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return TokenInfo.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) throws Exception {

        try {
            return SecurityUtils.getCurrentUser();
        }
        catch (Exception e) {
            return null;
        }
    }
}
