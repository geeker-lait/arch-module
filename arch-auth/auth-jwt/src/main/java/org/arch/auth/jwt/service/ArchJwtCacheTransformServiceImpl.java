package org.arch.auth.jwt.service;

import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.enums.LoginType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import top.dcenter.ums.security.jwt.api.cache.service.JwtCacheTransformService;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.ACCOUNT_ID;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.AUTHORITIES;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.AVATAR;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.LOGIN_TYPE;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.IDENTIFIER_ID;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.NICK_NAME;
import static org.arch.framework.ums.jwt.claim.JwtArchClaimNames.TENANT_ID;

/**
 * 如果是 jwt + session 模式, 缓存时转换为 {@link TokenInfo} 对象, 并进行序列化或反序列化.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.7 20:18
 */
public class ArchJwtCacheTransformServiceImpl implements JwtCacheTransformService<TokenInfo> {

    private final String principalClaimName;
    private final RedisSerializer<TokenInfo> redisSerializer;

    public ArchJwtCacheTransformServiceImpl(JwtProperties jwtProperties,
                                            @Qualifier("jwtTokenRedisSerializer")
                                            RedisSerializer<TokenInfo> redisSerializer) {
        this.principalClaimName = jwtProperties.getPrincipalClaimName();
        this.redisSerializer = redisSerializer;
    }


    @Override
    @NonNull
    public byte[] serialize(@NonNull Authentication authentication) throws SerializationException {
        if (authentication instanceof JwtAuthenticationToken)
        {
            byte[] result = redisSerializer.serialize(transform(authentication));
            if (nonNull(result)) {
                return result;
            }
        }
        throw new SerializationException("序列化错误");
    }

    @Override
    @NonNull
    public TokenInfo deserialize(@NonNull byte[] bytes) throws SerializationException {
        TokenInfo deserialize = redisSerializer.deserialize(bytes);
        if (nonNull(deserialize)) {
            return deserialize;
        }
        throw new SerializationException("反序列化错误");
    }

    @Override
    @NonNull
    public Class<TokenInfo> getClazz() {
        return TokenInfo.class;
    }

    private TokenInfo transform(Authentication authentication) throws SerializationException {
        if (authentication instanceof JwtAuthenticationToken)
        {
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            Jwt jwt = token.getToken();
            Collection<String> authorityList = jwt.getClaimAsStringList(AUTHORITIES.getClaimName());
            List<GrantedAuthority> authorities =
                    AuthorityUtils.createAuthorityList(authorityList.toArray(authorityList.toArray(new String[0])));
            return TokenInfo.builder()
                            .identifierId(Long.valueOf(jwt.getClaimAsString(IDENTIFIER_ID.getClaimName())))
                            .accountId(Long.valueOf(jwt.getClaimAsString(ACCOUNT_ID.getClaimName())))
                            .tenantId(Integer.valueOf(jwt.getClaimAsString(TENANT_ID.getClaimName())))
                            .accountName(jwt.getClaimAsString(principalClaimName))
                            .loginType(Integer.valueOf(jwt.getClaimAsString(LOGIN_TYPE.getClaimName())))
                            .nickName(jwt.getClaimAsString(NICK_NAME.getClaimName()))
                            .avatar(jwt.getClaimAsString(AVATAR.getClaimName()))
                            .authorities(authorities)
                            .build();
        }
        throw new SerializationException("必须是 org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken 类型");
    }
}
