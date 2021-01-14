package org.arch.framework.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

import static java.util.Objects.isNull;

/**
 * 添加 token 到 Feign {@link RequestTemplate} 的请求头上的拦截器.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.13 15:05
 */
public class TokenRequestInterceptor implements RequestInterceptor {

    private final String TOKEN_HEADER_NAME = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        setBearerToken2Header(template);
    }

    private void setBearerToken2Header(RequestTemplate template) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isNull(authentication)) {
            return;
        }

        if (authentication instanceof AbstractOAuth2TokenAuthenticationToken) {
            //noinspection unchecked
            AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token> token =
                    ((AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>) authentication);
            template.header(TOKEN_HEADER_NAME, token.getToken().getTokenValue());
        }

    }
}
