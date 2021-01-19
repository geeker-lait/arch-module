package org.arch.auth.jwt.service;

import org.springframework.lang.NonNull;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService;

import javax.servlet.http.HttpServletRequest;

/**
 * SDK 不支持曝露 jwk set uri, 默认无权限访问.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.18 9:42
 */
public class SdkJwkEndpointPermissionServiceImpl implements JwkEndpointPermissionService {
    @Override
    @NonNull
    public Boolean hasPermission(@NonNull HttpServletRequest request) {
        // SDK 不支持曝露 jwk set uri, 默认无权限访问.
        return Boolean.FALSE;
    }
}
