package org.arch.auth.sso.service;

import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;

import java.io.IOException;
import java.util.List;

/**
 * 用户登录与注册服务实现
 * @author YongWu zheng
 * @since 2021.1.3 11:42
 */
@RequiredArgsConstructor
public class ArchUserDetailsServiceImpl implements UmsUserDetailsService {

//    private final Biz

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return loadUserByUserId(username);
    }

    @Override
    public UserDetails loadUserByUserId(@NonNull String userId) throws UsernameNotFoundException {
        // TODO
        return null;
    }

    @Override
    public UserDetails registerUser(@NonNull String mobile) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public UserDetails registerUser(@NonNull ServletWebRequest request) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public UserDetails registerUser(@NonNull AuthUser authUser,
                                    @NonNull String username,
                                    @NonNull String defaultAuthority,
                                    @Nullable String decodeState) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public String[] generateUsernames(@NonNull AuthUser authUser) {
        // 待扩展
        return new String[]{
                authUser.getUsername(),
                // providerId = authUser.getSource()
                authUser.getUsername() + "_" + authUser.getSource(),
                // providerUserId = authUser.getUuid()
                authUser.getUsername() + "_" + authUser.getSource() + "_" + authUser.getUuid()
        };
    }

    @Override
    public List<Boolean> existedByUsernames(String... usernames) throws IOException {
        // TODO
        return null;
    }
}
