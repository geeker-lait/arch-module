package org.arch.auth.sso.oauth2.repository;

import org.arch.ums.feign.account.client.UmsAccountOauthTokenFeignService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.core.api.oauth.entity.AuthTokenPo;
import top.dcenter.ums.security.core.api.oauth.repository.jdbc.UsersConnectionTokenRepository;
import top.dcenter.ums.security.core.oauth.enums.EnableRefresh;
import top.dcenter.ums.security.core.oauth.repository.jdbc.Auth2JdbcUsersConnectionTokenRepository;

import java.util.List;

/**
 * 空实现, 目的替换 {@link Auth2JdbcUsersConnectionTokenRepository}, 相关功能通过 {@link UmsAccountOauthTokenFeignService} 实现.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 20:03
 */
@Component
public class InvalidUsersConnectionTokenRepository implements UsersConnectionTokenRepository {

    @Override
    public AuthTokenPo findAuthTokenById(String tokenId) throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }

    @Override
    public AuthTokenPo saveAuthToken(AuthTokenPo authToken) throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }

    @Override
    public AuthTokenPo updateAuthToken(AuthTokenPo authToken) throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }

    @Override
    public void delAuthTokenById(String tokenId) throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }

    @Override
    public Long getMaxTokenId() throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }

    @Override
    public List<AuthTokenPo> findAuthTokenByExpireTimeAndBetweenId(@NonNull Long expiredTime,
                                                                   @NonNull Long startId, @NonNull Long endId) throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }

    @Override
    public void updateEnableRefreshByTokenId(@NonNull EnableRefresh enableRefresh, @NonNull Long tokenId) throws Exception {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService interface");
    }
}
