package org.arch.auth.sso.oauth2.repository;

import org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl;
import org.arch.ums.feign.account.client.UmsAccountIdentifierFeignService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.MultiValueMap;
import top.dcenter.ums.security.core.api.oauth.entity.AuthTokenPo;
import top.dcenter.ums.security.core.api.oauth.entity.ConnectionData;
import top.dcenter.ums.security.core.api.oauth.entity.ConnectionKey;
import top.dcenter.ums.security.core.api.oauth.repository.exception.NoSuchConnectionException;
import top.dcenter.ums.security.core.api.oauth.repository.exception.NotConnectedException;
import top.dcenter.ums.security.core.api.oauth.repository.jdbc.UsersConnectionRepository;
import top.dcenter.ums.security.core.oauth.repository.jdbc.Auth2JdbcUsersConnectionRepository;

import java.util.List;
import java.util.Set;

/**
 * 空实现, 目的替换 {@link Auth2JdbcUsersConnectionRepository},
 * 相关功能通过 {@link UmsAccountIdentifierFeignService} 与 {@link ArchConnectionServiceImpl} 实现.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 20:03
 */
public class InvalidUsersConnectionRepository implements UsersConnectionRepository {

    @Override
    public List<ConnectionData> findConnectionByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public MultiValueMap<String, ConnectionData> findAllConnections(String userId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public List<ConnectionData> findConnections(String userId, String providerId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public MultiValueMap<String, ConnectionData> findConnectionsToUsers(String userId, MultiValueMap<String, String> providerUserIds) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData getPrimaryConnection(String userId, String providerId) throws NotConnectedException {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData addConnection(ConnectionData connection) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData updateConnection(ConnectionData connection) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public void removeConnections(String userId, String providerId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public void removeConnection(String userId, ConnectionKey connectionKey) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData findPrimaryConnection(String userId, String providerId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData getConnection(String userId, ConnectionKey connectionKey) throws NoSuchConnectionException {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public List<ConnectionData> findAllListConnections(String userId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public List<ConnectionData> findConnectionsToUsers(MapSqlParameterSource parameters, String providerUsersCriteriaSql, String userId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData updateConnectionByTokenId(AuthTokenPo token) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }

    @Override
    public ConnectionData findConnectionByTokenId(Long tokenId) {
        throw new RuntimeException("Please use the org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService" +
                                           "and org.arch.auth.sso.oauth2.service.ArchConnectionServiceImpl interface");
    }
}
