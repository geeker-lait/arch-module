package org.arch.auth.sso.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.properties.AppProperties;
import org.arch.framework.ums.enums.ScopesType;
import org.arch.ums.feign.account.client.UmsAccountOauthClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * jws set uri 访问权限访问服务接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 17:33
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArchJwkEndpointPermissionServiceImpl implements JwkEndpointPermissionService {

    private final UmsAccountOauthClient umsAccountOauthClient;
    private final AppProperties appProperties;

    @NonNull
    @Override
    public Boolean hasPermission(@NonNull HttpServletRequest request) {
        // 检查是否有访问 jws set uri 的访问权限, 权限存储在 AccountOauthClient 的 scopes 字段
        return hasPermissionOfApp(request);
    }

    /**
     * 检查是否有访问 jws set uri 的访问权限, 即 AccountOauthClient 的 appId 所对应的
     * scopes 字段(JWK,USER,UMS,..)中是否有 {@link ScopesType#JWK}.
     * @param request     {@link HttpServletRequest}
     * @return  返回 true 表示有访问权限.
     */
    @NonNull
    private Boolean hasPermissionOfApp(@NonNull HttpServletRequest request) {
        Set<String> scopeSet = getScopes(request);
        return scopeSet.contains(ScopesType.JWK.name());
    }

    @NonNull
    private Set<String> getScopes(@NonNull HttpServletRequest request) {
        String appId = request.getHeader(appProperties.getAppIdHeaderName());
        String appCode = request.getHeader(appProperties.getAppCodeHeaderName());
        return umsAccountOauthClient.getScopesByAppIdAndAppCode(appId, appCode);
    }
}
