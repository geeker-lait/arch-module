package org.arch.auth.sso.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import top.dcenter.ums.security.core.api.oauth.entity.ConnectionData;
import top.dcenter.ums.security.core.api.oauth.repository.exception.UpdateConnectionException;
import top.dcenter.ums.security.core.api.oauth.signup.ConnectionService;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;

import java.util.List;

/**
 * arch 第三方登录时, 自动注册, 更新第三方用户信息, 绑定第三方用户到本地的服务实现..
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.9 17:25
 */
//@Service
@RequiredArgsConstructor
@Slf4j
public class ArchConnectionServiceImpl implements ConnectionService {

    @Nullable
    @Override
    public UserDetails signUp(@NonNull AuthUser authUser, @NonNull String providerId, @NonNull String encodeState) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public void updateUserConnectionAndAuthToken(AuthUser authUser, ConnectionData connectionData) throws UpdateConnectionException {
        // TODO

    }

    @Override
    public void binding(@NonNull UserDetails principal, @NonNull AuthUser authUser, @NonNull String providerId) {
        // TODO

    }

    @Override
    public List<ConnectionData> findConnectionByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        // TODO
        return null;
    }
}
