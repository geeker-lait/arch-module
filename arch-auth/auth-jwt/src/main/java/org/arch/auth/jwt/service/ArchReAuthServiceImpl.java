package org.arch.auth.jwt.service;

import org.springframework.security.oauth2.jwt.Jwt;
import top.dcenter.ums.security.jwt.properties.JwtProperties;
import top.dcenter.ums.security.jwt.validator.UmsReAuthServiceImpl;

/**
 * JWT 是否要重新认证.<br>
 * 待实现: 支持对第三方认证的专门处理, 需要排除直接访问 redis 服务.
 *
 * @author YongWu zheng
 * @since 2021.1.3 21:19
 */
public class ArchReAuthServiceImpl extends UmsReAuthServiceImpl {


    public ArchReAuthServiceImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    @Override
    public Boolean isReAuth(Jwt jwt) {
        return super.isReAuth(jwt);
    }

}
