package org.arch.auth.sso.jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService;

import javax.servlet.http.HttpServletRequest;

/**
 * jws set uri 访问权限访问服务接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 17:33
 */
@Service
@Slf4j
public class ArchJwkEndpointPermissionServiceImpl implements JwkEndpointPermissionService {
    @NonNull
    @Override
    public Boolean hasPermission(@NonNull HttpServletRequest request) {
        // TODO
        return Boolean.TRUE;
    }
}
