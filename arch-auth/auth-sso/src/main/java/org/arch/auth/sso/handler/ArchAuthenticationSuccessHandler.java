package org.arch.auth.sso.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.common.consts.SecurityConstants;
import top.dcenter.ums.security.common.enums.LoginProcessType;
import top.dcenter.ums.security.common.utils.IpUtil;
import top.dcenter.ums.security.common.utils.UuidUtils;
import top.dcenter.ums.security.common.vo.ResponseResult;
import top.dcenter.ums.security.core.api.authentication.handler.BaseAuthenticationSuccessHandler;
import top.dcenter.ums.security.core.auth.properties.ClientProperties;
import top.dcenter.ums.security.core.oauth.properties.Auth2Properties;
import top.dcenter.ums.security.core.vo.AuthTokenVo;
import top.dcenter.ums.security.jwt.JwtContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasText;
import static top.dcenter.ums.security.common.utils.JsonUtil.isAjaxOrJson;
import static top.dcenter.ums.security.common.utils.JsonUtil.responseWithJson;
import static top.dcenter.ums.security.common.utils.JsonUtil.toJsonString;
import static top.dcenter.ums.security.core.util.MvcUtil.isSelfTopDomain;
import static top.dcenter.ums.security.core.util.RequestUtil.getRequestUri;
import static top.dcenter.ums.security.jwt.JwtContext.TEMPORARY_JWT_REFRESH_TOKEN;

/**
 *  arch 客户端认证成功处理器.<br><br>
 * @author  YongWu zheng
 * @since 2021.1.6 11:54
 */
@Component
@Slf4j
public class ArchAuthenticationSuccessHandler extends BaseAuthenticationSuccessHandler {

    protected final RequestCache requestCache;

    protected final LoginProcessType loginProcessType;

    private final RedisConnectionFactory redisConnectionFactory;

    private final SsoProperties ssoProperties;

    public ArchAuthenticationSuccessHandler(@NonNull ClientProperties clientProperties,
                                            @NonNull Auth2Properties auth2Properties,
                                            RedisConnectionFactory redisConnectionFactory, SsoProperties ssoProperties) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.ssoProperties = ssoProperties;
        this.auth2RedirectUrl = auth2Properties.getRedirectUrlPrefix();
        this.requestCache = new HttpSessionRequestCache();
        this.loginProcessType = clientProperties.getLoginProcessType();

        setTargetUrlParameter(clientProperties.getTargetUrlParameter());
        setUseReferer(clientProperties.getUseReferer());
        setAlwaysUseDefaultTargetUrl(clientProperties.getAlwaysUseDefaultTargetUrl());

        ignoreUrls.add(clientProperties.getLoginPage());
        ignoreUrls.add(clientProperties.getLogoutUrl());

        super.setDefaultTargetUrl(clientProperties.getSuccessUrl());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        if (log.isInfoEnabled()) {
            // 客户端成功处理器,
            log.info("登录成功: uid={}, ip={}, ua={}, sid={}",
                     authentication.getName(), IpUtil.getRealIp(request),
                     request.getHeader(SecurityConstants.HEADER_USER_AGENT), session.getId());
        }

        try
        {
            // 设置跳转的 url
            String targetUrl = determineTargetUrl(request, response);

            // 第三方登录成功请求, 返回 token 需特殊处理
            if (nonNull(auth2RedirectUrl)
                    && request.getServletPath().startsWith(auth2RedirectUrl)
                    && authentication instanceof AbstractOAuth2TokenAuthenticationToken) {
                //noinspection unchecked
                AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token> jwtAuthentication =
                        (AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>) authentication;
                String uuid = UuidUtils.getUUID();
                request.getSession().setAttribute(ssoProperties.getOauth2TokenParamName(), uuid);
                String tkValue = jwtAuthentication.getToken().getTokenValue();
                if (JwtContext.isRefreshJwtByRefreshToken()) {
                    String jwtRefreshTokenFromSession = JwtContext.getJwtRefreshTokenFromSession();
                    if (hasText(jwtRefreshTokenFromSession)) {
                        tkValue = tkValue.concat(ssoProperties.getDelimiterOfTokenAndRefreshToken())
                                         .concat(jwtRefreshTokenFromSession);
                    }
                    tkValue = tkValue.concat(ssoProperties.getDelimiterOfTokenAndRefreshToken())
                                     .concat(targetUrl);
                    getConnection().setEx((ssoProperties.getTempOauth2TokenPrefix() + uuid).getBytes(StandardCharsets.UTF_8),
                                          ssoProperties.getTempOauth2TokenTimeout().getSeconds(),
                                          tkValue.getBytes(StandardCharsets.UTF_8));
                }

                clearAuthenticationAttributes(request);
                request.getRequestDispatcher(ssoProperties.getAutoGetTokenUri()).forward(request, response);
                return ;
            }


            // 判断是否返回 json 类型 或 accept 是否要求返回 json
            if (LoginProcessType.JSON.equals(this.loginProcessType) || isAjaxOrJson(request))
            {
                clearAuthenticationAttributes(request);
                TokenInfo currentUser = SecurityUtils.getCurrentUser();
                AuthTokenVo authTokenVo = new AuthTokenVo(currentUser.getAccountId().toString(),
                                                          currentUser.getAccountName(),
                                                          null,
                                                          null,
                                                          null,
                                                          getJsonTargetUrl(targetUrl, request),
                                                          null);
                // 设置 jwt
                String jwtStringIfAllowBodyParameter = JwtContext.getJwtStringIfAllowBodyParameter(authentication);
                if (hasText(jwtStringIfAllowBodyParameter)) {
                    authTokenVo.setToken(jwtStringIfAllowBodyParameter);
                    authTokenVo.setExpiresIn(ofNullable(JwtContext.getJwtExpiresInByAuthentication(authentication)).orElse(-1L));
                }
                // 设置 jwt refresh token
                if (JwtContext.isRefreshJwtByRefreshToken()) {
                    authTokenVo.setRefreshToken(JwtContext.getJwtRefreshTokenFromSession());
                }

                clearAuthenticationAttributes(request);
                responseWithJson(response, HttpStatus.OK.value(),
                                 toJsonString(ResponseResult.success(null, authTokenVo)));
                return;
            }

            clearAuthenticationAttributes(request);
            session.removeAttribute(TEMPORARY_JWT_REFRESH_TOKEN);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
        catch (Exception e)
        {
            log.error(String.format("设置登录成功后跳转的URL失败: error=%s, uid=%s, ip=%s, ua=%s, sid=%s",
                                    e.getMessage(), authentication.getName(), IpUtil.getRealIp(request),
                                    request.getHeader(SecurityConstants.HEADER_USER_AGENT), session.getId()), e);
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }

    /**
     * Builds the target URL according to the logic defined in the main class Javadoc.
     */
    @SneakyThrows
    @Override
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response) {
        String defaultTargetUrl = getDefaultTargetUrl();
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);

        //noinspection AlibabaAvoidComplexCondition
        if (isAlwaysUseDefaultTargetUrl()) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Using default url %s", defaultTargetUrl));
            }
            this.requestCache.removeRequest(request, response);
            return defaultTargetUrl;
        }

        if (savedRequest != null)
        {
            final String redirectUrl = savedRequest.getRedirectUrl();
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("using url %s from default saved request %s", redirectUrl));
            }
            return redirectUrl;
        }

        // Check for the parameter and use that if available
        String targetUrl = null;

        String targetUrlParameter = getTargetUrlParameter();
        if (targetUrlParameter != null) {
            targetUrl = request.getParameter(targetUrlParameter);

            if (hasText(targetUrl) && isSelfTopDomain(targetUrl)) {
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace(LogMessage.format("Using url %s from request parameter %s", targetUrl,
                                                        targetUrlParameter));
                }
                return targetUrl;
            }
            targetUrl = null;
        }

        if (useReferer) {
            String referer = request.getHeader("Referer");
            if (hasText(referer) && isSelfTopDomain(referer))
            {
                targetUrl = referer;
            }
        }

        // 当 targetUrl 为 登录 url 时, 设置为 defaultTargetUrl
        if (!hasText(targetUrl) || isIgnoreUrl(targetUrl, request))
        {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Using default url %s", defaultTargetUrl));
            }
            return defaultTargetUrl;
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Using url %s from Referer header", targetUrl));
        }
        return targetUrl;
    }

    @Override
    public void setUseReferer(boolean useReferer) {
        super.setUseReferer(useReferer);
        this.useReferer = useReferer;
    }

    /**
     * 获取用于 json 的跳转地址
     */
    private String getJsonTargetUrl(String targetUrl, HttpServletRequest request) {
        if (!UrlUtils.isAbsoluteUrl(targetUrl))
        {
            String contextPath = request.getContextPath();
            if (!targetUrl.startsWith(contextPath)) {
                targetUrl = contextPath + targetUrl;
            }
        }
        return targetUrl;
    }

    /**
     * 判断 ignoreUrls 中是否包含 targetUrl
     * @param targetUrl 不能为 null
     * @return boolean
     */
    private boolean isIgnoreUrl(final String targetUrl, HttpServletRequest request) {
        String url = getRequestUri(targetUrl, request);
        return ignoreUrls.stream().anyMatch(url::startsWith);
    }

    @NonNull
    private RedisConnection getConnection() {
        return this.redisConnectionFactory.getConnection();
    }
}