package org.arch.auth.sso.utils;

import org.arch.framework.enums.AccountType;
import org.springframework.lang.Nullable;
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
    public static final String REGISTER_SOURCE_REQUEST_NAME = "source";

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
