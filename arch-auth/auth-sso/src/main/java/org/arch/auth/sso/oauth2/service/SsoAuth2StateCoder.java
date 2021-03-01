package org.arch.auth.sso.oauth2.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.ums.enums.AccountType;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.oauth.state.service.Auth2StateCoder;
import top.dcenter.ums.security.core.exception.LoginFailureException;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

/**
 * 对 OAuth2 login 流程中的 state 进行自定义编解码. 传递 accountType 信息.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.2 0:09
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SsoAuth2StateCoder implements Auth2StateCoder {

    public static final String SEPARATOR = "_";

    private final SsoProperties ssoProperties;

    @NonNull
    @Override
    public String encode(@NonNull String state, @NonNull HttpServletRequest request) {

        // 获取注册的账户类型
        AccountType accountType = RegisterUtils.getAccountType(ssoProperties.getAccountTypeParameterName());
        if (isNull(accountType)) {
            log.warn("获取注册的账户类型失败: accountType 没有传递 或 格式错误");
            throw new LoginFailureException(ErrorCodeEnum.PARAMETER_ERROR, "accountType 没有传递 或 格式错误", null);
        }
        return accountType.name() + SEPARATOR + state;
    }

    @NonNull
    @Override
    public String decode(@NonNull String encoderState) {
        int indexOf = encoderState.indexOf(SEPARATOR);
        return encoderState.substring(0, indexOf);
    }
}
