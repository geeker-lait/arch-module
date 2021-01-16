package org.arch.auth.sso.utils;

import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 注册工具
 * @author YongWu zheng
 * @since 2021.1.3 15:12
 */
public class RegisterUtils {
    /**
     * 账号类型 header 参数名
     */
    public static final String ACCOUNT_TYPE_HEADER_NAME = "AccountType";

    /**
     * 注册推广来源类型 request 参数名
     */
    public static final String REGISTER_SOURCE_REQUEST_NAME = "_from";
    /**
     * 注册推广来源类型中用户推荐类型的前缀, 默认: user_ .
     * 如果用户 ID 为 001, 则-用户的推荐类型为: user_001
     */
    public static final String USER_RECOMMEND_SOURCE_PREFIX = "user_";

    /**
     * 从 request 中 获取账号类型.
     * @return  返回 {@link AccountType} , 不存在则返回 null.
     */
    @Nullable
    public static AccountType getAccountType() {

        String accountTypeString = getValueFromRequest(ACCOUNT_TYPE_HEADER_NAME, TRUE);

        if (hasText(accountTypeString)) {
            return AccountType.valueOf(accountTypeString.trim().toUpperCase());
        }

        return null;
    }

    /**
     * 从 request 中 获取来源类型.
     * @return  返回来源 , 不存在则返回 null.
     */
    @Nullable
    public static String getSource() {
        return getValueFromRequest(REGISTER_SOURCE_REQUEST_NAME, FALSE);
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
     * 从 request 中获取指定参数值
     * @param paramName 参数名称
     * @param isHeader  是否从请求头中获取
     * @return  返回指定参数名称的值, 如果不存在对应的值返回 null
     */
    @Nullable
    private static String getValueFromRequest(@NotNull String paramName, @NotNull Boolean isHeader) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (isNull(requestAttributes)) {
            return null;
        }

        HttpServletRequest request = requestAttributes.getRequest();

        if (!isHeader) {
            return request.getParameter(paramName);
        }
        else {
            return request.getHeader(paramName);
        }
    }



}
