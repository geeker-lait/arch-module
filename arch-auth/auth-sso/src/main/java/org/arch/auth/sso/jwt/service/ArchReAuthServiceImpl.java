package org.arch.auth.sso.jwt.service;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.jwt.api.validator.service.ReAuthService;
import top.dcenter.ums.security.jwt.properties.JwtBlacklistProperties;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;

/**
 * JWT 是否要重新认证.
 * @author YongWu zheng
 * @since 2021.1.3 21:19
 */
@Service
public class ArchReAuthServiceImpl implements ReAuthService {

    private final JwtBlacklistProperties blacklistProperties;
    private final RedisConnectionFactory redisConnectionFactory;

    public ArchReAuthServiceImpl(JwtProperties jwtProperties, RedisConnectionFactory redisConnectionFactory) {
        this.blacklistProperties = jwtProperties.blacklist;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public Boolean isReAuth(Jwt jwt) {
        Instant expiresAt = jwt.getExpiresAt();
        if (isNull(expiresAt) || Instant.now().isAfter(expiresAt)) {
            return TRUE;
        }
        if (blacklistProperties.getEnable()) {
            return FALSE;
        }
        // TODO 需要 redis 添加是否 reAuth 标志, 如果有此标志, 需要同时删除 refreshToken
        if (isNull(getConnection().get(jwt.getTokenValue().getBytes(StandardCharsets.UTF_8)))) {
            return TRUE;
        }
        return FALSE;
    }

    private RedisConnection getConnection() {
        return redisConnectionFactory.getConnection();
    }

}
