package org.arch.framework.utils;

import org.arch.framework.api.IdKey;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.enums.LoginType;
import org.arch.framework.ums.enums.Role;
import org.arch.framework.ums.jwt.claim.JwtArchClaimNames;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.EXTRACT_ACCOUNT_TYPE;
import static org.arch.framework.ums.consts.RoleConstants.ROLE_PREFIX;
import static org.arch.framework.ums.consts.RoleConstants.TENANT_PREFIX;

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
     * 获取 jwt token value
     * @return jwt token value
     */
    @Nullable
    public static String getJwtString() {
        SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        if (authentication instanceof AbstractOAuth2TokenAuthenticationToken) {
            return ((AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>) authentication).getToken()
                                                                                                 .getTokenValue();
        }
        return null;
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
     * 获取角色的租户权限
     * @return  租户权限.
     */
    @Nullable
    public static String getCurrentTenantAuthority() {
        TokenInfo currentUser = getCurrentUser();
        Collection<GrantedAuthority> authorities = currentUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String authorityStr = authority.getAuthority();
            if (authorityStr.startsWith(TENANT_PREFIX)) {
                return authorityStr;
            }
        }
        return null;
    }

    /**
     * 获取角色的租户权限
     * @return  租户权限.
     */
    @Nullable
    public static String getTenantAuthority(@NonNull Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String authorityStr = authority.getAuthority();
            if (authorityStr.startsWith(TENANT_PREFIX)) {
                return authorityStr;
            }
        }
        return null;
    }

    /**
     * 检查是否有 ADMIN 角色
     * @param token 登录用户的 {@link TokenInfo}
     * @return  true 表示为 ADMIN 或 SUPER_ADMIN 角色.
     */
    public static boolean isAdminForRole(@NonNull TokenInfo token) {
        // 权限校验
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority(ROLE_PREFIX + Role.ADMIN.name());
        SimpleGrantedAuthority superAdmin = new SimpleGrantedAuthority(ROLE_PREFIX + Role.SUPER_ADMIN.name());
        return authorities.contains(admin) || authorities.contains(superAdmin);
    }

    /**
     * 根据 aid(账号id/用户id/会员id/商户id) 提取 {@link AccountType}
     * @param aid    {@link IdService#generateId(IdKey)} 生成的 账号id/用户id/会员id/商户id
     * @return  {@link AccountType}
     */
    @NonNull
    public static AccountType extractAccountTypeByAid(@NonNull Long aid) {
        String id = aid.toString();
        // 账号 id 必为 17 位
        // 17 = 年  日  日内的秒数 redis原子自增(20 234 86399 0000001)
        Optional<AccountType> accountTypeOpt =
                Arrays.stream(AccountType.values())
                      .filter(type -> {
                          int idLengthNonBizPrefix = type.getIdLengthNonBizPrefix();
                          int end = id.length() - idLengthNonBizPrefix;
                          if (end < 1) {
                              throw new BusinessException(EXTRACT_ACCOUNT_TYPE, new Object[]{aid}, "提取账号类型失败");
                          }
                          String bizPrefix = id.substring(0, end);


                          if (bizPrefix.equals(type.getIdKey().getBizPrefix())) {
                              return true;
                          }
                          return false;
                      })
                      .findFirst();

        if (accountTypeOpt.isPresent()) {
            return accountTypeOpt.get();
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
        Integer loginType = Integer.valueOf(jwt.getClaimAsString(JwtArchClaimNames.LOGIN_TYPE.getClaimName()));
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

    /**
     * 如果 {@link Authentication} 为 {@link AbstractOAuth2TokenAuthenticationToken<Jwt>} 且 {@link Jwt} 已过期, 那么清除
     * 此 {@link AbstractOAuth2TokenAuthenticationToken<Jwt>},
     * 再设置 {@link Authentication} 为 {@link AnonymousAuthenticationToken}.<br>
     * 防止因 session 中的 Jwt 过期而无法查询用户信息. <br>
     * 注意: 适合登录查询时使用.
     * @param clockSkew 授权服务器的时钟与资源服务器的时钟可能存在偏差, 设置时钟偏移量以消除不同服务器间的时钟偏差的影响,
     *                  通过属性 ums.jwt.clockSkew 设置.
     */
    public static void ifExpiredOfJwtThenClearAuthentication(Duration clockSkew) {
        SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        if (authentication instanceof AbstractOAuth2TokenAuthenticationToken)
        {
            //noinspection unchecked
            AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token> token =
                    ((AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>) authentication);
            // 判断 jwt 是否过期.
            AbstractOAuth2Token oAuth2Token = token.getToken();
            if (oAuth2Token instanceof Jwt)
            {
                Jwt jwt = ((Jwt) oAuth2Token);
                Instant expiresAt = jwt.getExpiresAt();
                if (nonNull(expiresAt) && Instant.now().minusSeconds(clockSkew.getSeconds()).isAfter(expiresAt)) {
                    context.setAuthentication(
                            new AnonymousAuthenticationToken(token.getName(),
                                                             token.getName(),
                                                             AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
                }
            }
        }
    }
}
