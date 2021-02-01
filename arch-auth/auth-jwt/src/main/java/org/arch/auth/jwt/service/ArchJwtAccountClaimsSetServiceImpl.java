package org.arch.auth.jwt.service;


import com.nimbusds.jwt.JWTClaimsSet;
import org.arch.framework.ums.jwt.claim.JwtArchClaimNames;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import top.dcenter.ums.security.jwt.api.claims.service.CustomClaimsSetService;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

import java.time.Instant;

/**
 * arch {@link Jwt} 中与 {@code AccountIdentifier} 相关的 ClaimsSet 服务实现
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 16:26
 */
public class ArchJwtAccountClaimsSetServiceImpl implements CustomClaimsSetService {

    private final String principalClaimName;

    public ArchJwtAccountClaimsSetServiceImpl(JwtProperties jwtProperties) {
        this.principalClaimName = jwtProperties.getPrincipalClaimName();
    }

    @NonNull
    @Override
    public JWTClaimsSet toClaimsSet(@NonNull Authentication authentication) {
        // Prepare JWT with claims set
        JWTClaimsSet.Builder builder = getJwtClaimsSetBuilderWithAuthorities(authentication.getAuthorities());
        Object principal = authentication.getPrincipal();
        if (principal instanceof ArchUser) {
            ArchUser user = ((ArchUser) principal);
            extractedClaimsSet2Builder(user, builder);
        }
        else {
            throw new RuntimeException("Authentication.principal 必须是 org.arch.framework.userdetails.ArchUser");
        }
        return builder.build();
    }

    @NonNull
    @Override
    public JWTClaimsSet toClaimsSet(@NonNull UserDetails userDetails) {
        // Prepare JWT with claims set
        JWTClaimsSet.Builder builder = getJwtClaimsSetBuilderWithAuthorities(userDetails.getAuthorities());
        if (!(userDetails instanceof ArchUser)) {
            throw new RuntimeException("UserDetails 必须是 org.arch.framework.userdetails.ArchUser");
        }
        extractedClaimsSet2Builder(userDetails, builder);
        return builder.build();
    }

    private void extractedClaimsSet2Builder(@NonNull UserDetails userDetails, @NonNull JWTClaimsSet.Builder builder) {
        if (userDetails instanceof ArchUser) {
            ArchUser user = ((ArchUser) userDetails);
            builder.claim(JwtArchClaimNames.ACCOUNT_ID.getClaimName(), user.getAccountId());
            builder.claim(JwtArchClaimNames.TENANT_ID.getClaimName(), user.getTenantId());
            builder.claim(JwtArchClaimNames.CHANNEL_TYPE.getClaimName(), user.getChannelType());
            builder.claim(JwtArchClaimNames.NICK_NAME.getClaimName(), user.getNickName());
            builder.claim(JwtArchClaimNames.AVATAR.getClaimName(), user.getAvatar());
            builder.claim(principalClaimName, user.getUsername());
        }
        builder.claim(JwtClaimNames.IAT, Instant.now().getEpochSecond());
    }

}
