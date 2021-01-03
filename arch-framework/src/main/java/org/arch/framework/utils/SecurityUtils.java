package org.arch.framework.utils;

import org.arch.framework.bean.TokenInfo;
import org.arch.framework.enums.ChannelType;
import org.arch.framework.exception.UnAuthenticationException;
import org.arch.framework.jwt.claim.JwtArchClaimNames;
import org.arch.framework.userdetails.ArchUser;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
     * {@link ArchUser} 转换为 {@link TokenInfo}
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
                        .accountName(jwt.getClaimAsString(JwtArchClaimNames.ACCOUNT_NAME.getClaimName()))
                        .channelType(channelType)
                        .authorities(authentication.getAuthorities())
                        .build();
    }

}
