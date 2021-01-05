package org.arch.auth.sso.jwt.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.jwt.properties.JwtProperties;
import top.dcenter.ums.security.jwt.validator.UmsReAuthServiceImpl;

/**
 * JWT 是否要重新认证.
 * @author YongWu zheng
 * @since 2021.1.3 21:19
 */
@Service
public class ArchReAuthServiceImpl extends UmsReAuthServiceImpl {


    public ArchReAuthServiceImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    @Override
    public Boolean isReAuth(Jwt jwt) {
        return super.isReAuth(jwt);
    }

}
