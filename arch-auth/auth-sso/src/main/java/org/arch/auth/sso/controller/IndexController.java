package org.arch.auth.sso.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.dcenter.ums.security.jwt.properties.BearerTokenProperties;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;
import static top.dcenter.ums.security.jwt.JwtContext.TEMPORARY_JWT_REFRESH_TOKEN;
import static top.dcenter.ums.security.jwt.enums.JwtRefreshHandlerPolicy.AUTO_RENEW;
import static top.dcenter.ums.security.jwt.enums.JwtRefreshHandlerPolicy.REFRESH_TOKEN;

/**
 * 首页控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 18:52
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@Api("首页")
public class IndexController {

    private final JwtProperties jwtProperties;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken)
        {
            JwtAuthenticationToken token = ((JwtAuthenticationToken) authentication);
            setBearerTokenAndRefreshTokenToHeader(token.getToken(), null);
        }
        return "index";
    }

    private void setBearerTokenAndRefreshTokenToHeader(@NonNull Jwt jwt,
                                                       @Nullable Jwt refreshTokenJwt) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        // 获取 bearerToken
        String bearerToken = "bearer " + jwt.getTokenValue();
        BearerTokenProperties bearerTokenProperties = jwtProperties.getBearer();

        if (nonNull(response) && nonNull(refreshTokenJwt)) {
            // 设置 refreshToken 到 header
            if (REFRESH_TOKEN.equals(jwtProperties.getRefreshHandlerPolicy())) {
                // 缓存进 redis
                String refreshTokenHeaderName = bearerTokenProperties.getRefreshTokenHeaderName();
                String refreshTokenJwtValue = refreshTokenJwt.getTokenValue();
                if (!bearerTokenProperties.getAllowFormEncodedBodyParameter()) {
                    // 设置到请求头
                    response.setHeader(refreshTokenHeaderName, refreshTokenJwtValue);
                }
                else {
                    // 临时设置到 session, 再通过认证成功处理器获取 refresh token 通过 json 返回
                    requestAttributes.setAttribute(TEMPORARY_JWT_REFRESH_TOKEN, refreshTokenJwtValue, SCOPE_SESSION);
                }
            }
        }

        // 当 jwt 刷新策略为 JwtRefreshHandlerPolicy#AUTO_RENEW 时, 刷新的 jwt 直接设置到 header 中, 前端可以从相应的 header 中获取.
        boolean isAutoRenewPolicy = AUTO_RENEW.equals(jwtProperties.getRefreshHandlerPolicy());

        if (!bearerTokenProperties.getAllowFormEncodedBodyParameter() || isAutoRenewPolicy) {
            String bearerTokenHeaderName = bearerTokenProperties.getBearerTokenHeaderName();
            response.setHeader(bearerTokenHeaderName, bearerToken);
        }
    }
}
