package org.arch.framework.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;

/**
 * Feign 全局请求拦截器.<br>
 * 1. 添加 token 到 Feign {@link RequestTemplate} 的请求头,<br>
 * 2. 添加 tenantId 到 Feign {@link RequestTemplate} 的请求头.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.13 15:05
 */
public class FeignGlobalRequestInterceptor implements RequestInterceptor {

    private static final String TOKEN_HEADER_NAME = "Authorization";
    private static final String BEARER = "bearer ";
    private final TenantContextHolder tenantContextHolder;
    private final String tenantHeaderName;

    public FeignGlobalRequestInterceptor(TenantContextHolder tenantContextHolder,
                                         String tenantHeaderName) {
        this.tenantContextHolder = tenantContextHolder;
        this.tenantHeaderName = tenantHeaderName;
    }


    @Override
    public void apply(RequestTemplate template) {
        setBearerToken2Header(template);
    }

    private void setBearerToken2Header(RequestTemplate template) {
        // 设置 tenantId
        template.header(tenantHeaderName, tenantContextHolder.getTenantId());

        // 设置 token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNull(authentication)) {
            return;
        }

        if (authentication instanceof AbstractOAuth2TokenAuthenticationToken) {
            //noinspection unchecked
            AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token> token =
                    ((AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>) authentication);
            template.header(TOKEN_HEADER_NAME, BEARER.concat(token.getToken().getTokenValue()));
        }

    }
}
