package org.arch.framework.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.arch.framework.beans.exception.AuthenticationException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import top.dcenter.ums.security.common.consts.MdcConstants;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.time.Duration;
import java.time.Instant;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.ums.consts.MdcConstants.MDC_KEY;
import static org.springframework.util.StringUtils.hasText;

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
    /**
     * 授权服务器的时钟与资源服务器的时钟可能存在偏差, 设置时钟偏移量以消除不同服务器间的时钟偏差的影响, 通过属性 ums.jwt.clockSkew 设置.
     */
    private final Duration clockSkew;

    public FeignGlobalRequestInterceptor(TenantContextHolder tenantContextHolder,
                                         String tenantHeaderName, Duration clockSkew) {
        this.tenantContextHolder = tenantContextHolder;
        this.tenantHeaderName = tenantHeaderName;
        this.clockSkew = clockSkew;
    }


    @Override
    public void apply(RequestTemplate template) {
        setBearerToken2Header(template);
    }

    private void setBearerToken2Header(RequestTemplate template) {
        // 设置 tenantId
        template.header(tenantHeaderName, tenantContextHolder.getTenantId());

        // 设置 MDC 日志链路追踪 ID
        String mdcId = MDC.get(MdcConstants.MDC_KEY);
        if (hasText(mdcId)) {
            template.header(MDC_KEY, mdcId);
        }

        // 设置 token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNull(authentication)) {
            return;
        }
        if (authentication instanceof AbstractOAuth2TokenAuthenticationToken) {
            //noinspection unchecked
            AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token> token =
                    ((AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>) authentication);
            // 判断 jwt 是否过期.
            AbstractOAuth2Token oAuth2Token = token.getToken();
            if (oAuth2Token instanceof Jwt)
            {
                Jwt jwt = ((Jwt) oAuth2Token);
                Instant expiresAt = jwt.getExpiresAt();
                if (nonNull(expiresAt) && Instant.now().minusSeconds(this.clockSkew.getSeconds()).isAfter(expiresAt)) {
                    throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "JWT 过期");
                }
            }
            template.header(TOKEN_HEADER_NAME, BEARER.concat(oAuth2Token.getTokenValue()));
        }

    }
}
