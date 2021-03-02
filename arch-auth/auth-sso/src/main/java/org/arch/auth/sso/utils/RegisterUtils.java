package org.arch.auth.sso.utils;

import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.entity.OauthToken;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.dcenter.ums.security.core.api.oauth.justauth.request.Auth2DefaultRequest;
import top.dcenter.ums.security.core.mdc.MdcIdType;
import top.dcenter.ums.security.core.mdc.utils.MdcUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static org.arch.framework.ums.consts.RoleConstants.AUTHORITY_SEPARATOR;
import static org.arch.framework.ums.consts.RoleConstants.TENANT_PREFIX;
import static org.springframework.util.StringUtils.hasText;
import static top.dcenter.ums.security.common.consts.MdcConstants.MDC_KEY;

/**
 * 注册工具
 * @author YongWu zheng
 * @since 2021.1.3 15:12
 */
public class RegisterUtils {

    /**
     * 注册推广来源类型中用户推荐类型的前缀, 默认: user_ .
     * 如果用户 ID 为 001, 则-用户的推荐类型为: user_001
     */
    public static final String USER_RECOMMEND_SOURCE_PREFIX = "user_";

    /**
     * 第三方用户 identifier(账号-标识) 分隔符
     */
    public static final String OAUTH_IDENTIFIER_SEPARATOR = "_";

    /**
     * 生成第三方用户 identifier(账号-标识)
     * @param provider          第三方服务商
     * @param providerUserId    用户在第三方服务商的用户 ID
     * @return  identifier(账号-标识)
     */
    @NonNull
    public static String getIdentifierForOauth2(@NonNull String provider, @NonNull String providerUserId) {
        return provider + OAUTH_IDENTIFIER_SEPARATOR + providerUserId;
    }

    /**
     * 从 request 中 获取账号类型.
     * @param accountTypeName 账号类型参数名
     * @return  返回 {@link AccountType} , 不存在则返回 null.
     */
    @Nullable
    public static AccountType getAccountType(@NonNull String accountTypeName) {

        String accountTypeString = getValueFromRequest(accountTypeName);
        if (hasText(accountTypeString)) {
            return AccountType.getAccountType(accountTypeString);
        }
        return null;
    }

    /**
     * 从 request 中 获取来源类型.
     * @param sourceName 账号类型参数名
     * @return  返回来源 , 不存在则返回 null.
     */
    @Nullable
    public static String getSource(@NonNull String sourceName) {
        return getValueFromRequest(sourceName);
    }

    /**
     * 获取默认的用户权限
     * @param ssoProperties {@link SsoProperties}
     * @param tenantId      租户 ID
     * @return  默认的用户权限
     */
    @NonNull
    public static String getDefaultAuthorities(@NonNull SsoProperties ssoProperties, @NonNull String tenantId) {
        // 构建默认的用户权限
        return getDefaultAuthorities(ssoProperties.getDefaultAuthorities(), tenantId);
    }

    /**
     * 获取默认的用户权限
     * @param defaultAuthority  用户默认权限
     * @param tenantId          租户 ID
     * @return  默认的用户权限
     */
    @NonNull
    public static String getDefaultAuthorities(@NonNull String defaultAuthority, @NonNull String tenantId) {
        // 构建默认的用户权限
        return defaultAuthority + AUTHORITY_SEPARATOR + TENANT_PREFIX + tenantId;
    }

    /**
     * 生成推广来源类型中用户推荐类型, 只有在用户登录情况下才会生成, 未登录情况下生成返回 null.
     * @return  返回用户推荐类型字符串, 未登录情况下生成返回 null.
     */
    @Nullable
    public static String generateUserSource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof ArchUser)
            {
                ArchUser archUser = ((ArchUser) principal);
                return USER_RECOMMEND_SOURCE_PREFIX.concat(archUser.getAccountId().toString());
            }
        }
        return null;
    }

    /**
     * 从 request 中获取指定参数值, 会从 header 或 请求参数中获取
     * @param paramName 参数名称
     * @return  返回指定参数名称的值, 如果不存在对应的值返回 null
     */
    @Nullable
    private static String getValueFromRequest(@NotNull String paramName) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (isNull(requestAttributes)) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String parameter = request.getParameter(paramName);
        if (hasText(parameter)) {
        	return parameter;
        }
        return request.getHeader(paramName);

    }

    /**
     * 转换为第三方用户的 OauthToken 信息
     *
     * @param authUser     第三方用户信息
     * @param tenantId     租户 ID
     * @param identifierId {@link Identifier#getId()}
     * @param timeout      {@link HttpConfig#getTimeout()}
     */
    @NonNull
    public static OauthToken toOauthToken(@NonNull AuthUser authUser, @NonNull String tenantId,
                                          @NonNull Long identifierId, int timeout) {

        // 添加到 account_auth_token 表
        // 获取 AuthTokenPo
        AuthToken token = authUser.getToken();
        OauthToken oauthToken = new OauthToken();
        BeanUtils.copyProperties(token, oauthToken);
        oauthToken.setProviderId(authUser.getSource())
                  .setAccountIdentifierId(identifierId)
                  .setTenantId(Integer.valueOf(tenantId))
                  .setEnableRefresh(true)
                  .setSt(LocalDateTime.now());

        // 有效期转时间戳
        oauthToken.setExpireTime(Auth2DefaultRequest.expireIn2Timestamp(timeout, token.getExpireIn()));

        return oauthToken;
    }

    /**
     * 获取 MDC 调用链路追踪 ID
     * @return  MDC 调用链路追踪 ID
     */
    @NonNull
    public static String getTraceId() {
        String id = MDC.get(MDC_KEY);
        if (hasText(id)) {
            return id;
        }
        return MdcUtil.getMdcId(MdcIdType.UUID, null);
    }

}
