package org.arch.framework.utils;

import org.arch.framework.api.IdKey;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.enums.LoginType;
import org.arch.framework.ums.jwt.claim.JwtArchClaimNames;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.arch.framework.beans.exception.constant.CommonStatusCode.EXTRACT_ACCOUNT_TYPE;

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
     * @throws AuthenticationException 未登录异常
     */
    @NonNull
    public static TokenInfo getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "当前登录状态过期");
        }
        if (authentication instanceof JwtAuthenticationToken) {
            return toTokenInfoFromUserDetails((JwtAuthenticationToken) authentication);
        }
        throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "找不到当前登录的信息");
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    @NonNull
    public static String getAccountName() {
        TokenInfo tokenInfo = getCurrentUser();
        return tokenInfo.getAccountName();
    }

    /**
     * 获取系统用户ID, 这里的 userId 即 accountId
     *
     * @return 系统用户ID, 这里的 userId 即 accountId
     */
    @NonNull
    public static Long getCurrentUserId() {
        TokenInfo tokenInfo = getCurrentUser();
        return tokenInfo.getUserId();
    }

    /**
     * 获取当前账号的 {@link AccountType}
     * @return  {@link AccountType}
     */
    @NonNull
    public static AccountType getAccountType() {
        Long currentUserId = getCurrentUserId();
        return extractAccountTypeByAid(currentUserId);
    }

    /**
     * 根据 aid(账号id/用户id/会员id/商户id) 提取 {@link AccountType}
     * @param aid    {@link IdService#generateId(IdKey)} 生成的 账号id/用户id/会员id/商户id
     * @return  {@link AccountType}
     */
    @NonNull
    public static AccountType extractAccountTypeByAid(@NonNull Long aid) {
        String id = aid.toString();
        // 17 = 年  日  日内的秒数 redis原子自增(20 234 86399 0000001)
        int end = id.length() - 17;
        if (end < 1) {
            throw new BusinessException(EXTRACT_ACCOUNT_TYPE, new Object[]{aid}, "提取账号类型失败");
        }
        String bizPrefix = id.substring(0, end);

        if (bizPrefix.equals(IdKey.UMS_MEMBER_ID.getBizPrefix())) {
            return AccountType.MEMBER;
        }
        if (bizPrefix.equals(IdKey.UMS_MERCHANT_ID.getBizPrefix())) {
            return AccountType.MERCHANT;
        }
        if (bizPrefix.equals(IdKey.UMS_ACCOUNT_ID.getBizPrefix())) {
            return AccountType.ACCOUNT;
        }
        if (bizPrefix.equals(IdKey.UMS_USER_ID.getBizPrefix())) {
            return AccountType.USER;
        }
        throw new BusinessException(EXTRACT_ACCOUNT_TYPE, new Object[]{aid}, "提取账号类型失败");
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
        Long identifierId = Long.valueOf(jwt.getClaimAsString(JwtArchClaimNames.IDENTIFIER_ID.getClaimName()));
        Integer tenantId = Integer.valueOf(jwt.getClaimAsString(JwtArchClaimNames.TENANT_ID.getClaimName()));
        LoginType loginType = LoginType.valueOf(jwt.getClaimAsString(JwtArchClaimNames.LOGIN_TYPE.getClaimName()));
        return TokenInfo.builder()
                        .identifierId(identifierId)
                        .accountId(accountId)
                        .tenantId(tenantId)
                        // 这里的 ClaimName 必须与属性 ums.jwt.principalClaimName 值相同.
                        .accountName(jwt.getClaimAsString(JwtClaimNames.SUB))
                        .loginType(loginType)
                        .nickName(jwt.getClaimAsString(JwtArchClaimNames.NICK_NAME.getClaimName()))
                        .avatar(jwt.getClaimAsString(JwtArchClaimNames.AVATAR.getClaimName()))
                        .authorities(authentication.getAuthorities())
                        .build();
    }
}
