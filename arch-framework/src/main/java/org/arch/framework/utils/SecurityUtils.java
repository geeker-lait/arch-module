package org.arch.framework.utils;

import org.arch.framework.exception.UnAuthenticationException;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.enums.ChannelType;
import org.arch.framework.ums.jwt.claim.JwtArchClaimNames;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * 获取当前登录的用户
 * @author YongWu zheng
 * @since 2021-01-03 17:36
 */
public class SecurityUtils {

    /**
     * 获取当前登录的账户信息
     *
     * @return  {@link TokenInfo}
     * @throws UnAuthenticationException 未登录异常
     */
    @NonNull
    public static TokenInfo getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthenticationException(UNAUTHORIZED.value(), "当前登录状态过期");
        }
        if (authentication instanceof JwtAuthenticationToken) {
            return toTokenInfoFromUserDetails((JwtAuthenticationToken) authentication);
        }
        throw new UnAuthenticationException(UNAUTHORIZED.value(), "找不到当前登录的信息");
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static String getAccountName() {
        TokenInfo tokenInfo = getCurrentUser();
        return tokenInfo.getAccountName();
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        TokenInfo tokenInfo = getCurrentUser();
        return tokenInfo.getUserId();
    }

    /**
     * {@link ArchUser} 转换为 {@link TokenInfo}. <br>
     * 注意: {@link TokenInfo#getAccountName()} 对应于 {@code AccountIdentifier#getIdentifier()},
     * 值必须与 ums.jwt.principalClaimName 值相同.
     *
     * @param authentication {@link JwtAuthenticationToken}
     * @return  返回 {@link TokenInfo} 对象
     */
    @NonNull
    private static TokenInfo toTokenInfoFromUserDetails(@NonNull JwtAuthenticationToken authentication) {
        Jwt jwt = authentication.getToken();
        Long accountId = Long.valueOf(jwt.getClaimAsString(JwtArchClaimNames.ACCOUNT_ID.getClaimName()));
        ChannelType channelType = ChannelType.valueOf(jwt.getClaimAsString(JwtArchClaimNames.CHANNEL_TYPE.getClaimName()));
        return TokenInfo.builder()
                        .accountId(accountId)
                        // 这里的 ClaimName 必须与属性 ums.jwt.principalClaimName 值相同.
                        .accountName(jwt.getClaimAsString(JwtClaimNames.SUB))
                        .channelType(channelType)
                        .authorities(authentication.getAuthorities())
                        .build();
    }
}
