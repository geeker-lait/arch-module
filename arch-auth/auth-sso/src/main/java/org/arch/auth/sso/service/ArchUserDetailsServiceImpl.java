package org.arch.auth.sso.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.arch.framework.enums.ChannelType;
import org.arch.framework.id.IdKey;
import org.arch.framework.id.IdService;
import org.arch.framework.userdetails.ArchUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.auth.properties.ClientProperties;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;
import top.dcenter.ums.security.core.exception.UserNotExistException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录与注册服务实现
 * @author YongWu zheng
 * @since 2021.1.3 11:42
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ArchUserDetailsServiceImpl implements UmsUserDetailsService {

//    private final Biz
    /**
     * 用于密码加解密
     */
    private final PasswordEncoder passwordEncoder;
    private final ClientProperties clientProperties;
    private final IdService idService;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return loadUserByUserId(username);
    }

    @Override
    public UserDetails loadUserByUserId(@NonNull String userId) throws UsernameNotFoundException {
        // TODO
        try
        {
            // 从缓存中查询用户信息:
            // 根据用户名获取用户信息

            // 获取用户信息逻辑。。。
            // ...

            // 示例：只是从用户登录日志表中提取的信息，
            log.info("Demo ======>: 登录用户名：{}, 登录成功", userId);
            return new ArchUser(userId,
                                passwordEncoder.encode("admin"),
                                1L,
                                ChannelType.ACCOUNT,
                                true,
                                true,
                                true,
                                true,
                                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

        }
        catch (Exception e)
        {
            String msg = String.format("Demo ======>: 登录用户名：%s, 登录失败: %s", userId, e.getMessage());
            log.error(msg);
            throw new UserNotExistException(ErrorCodeEnum.QUERY_USER_INFO_ERROR, e, userId);
        }
    }

    @Override
    public UserDetails registerUser(@NonNull String mobile) throws RegisterUserFailureException {
        // todo
        // 示例：
        log.info("Demo ======>: 注册用户名：{}, 注册成功", mobile);
        return new ArchUser(mobile,
                            passwordEncoder.encode("admin"),
                            Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_ID)),
                            ChannelType.PHONE,
                            true,
                            true,
                            true,
                            true,
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

    @Override
    public UserDetails registerUser(@NonNull ServletWebRequest request) throws RegisterUserFailureException {
        // TODO
        String username = request.getParameter(clientProperties.getUsernameParameter());
        // 示例：
        log.info("Demo ======>: 注册用户名：{}, 注册成功", username);
        return new ArchUser(username,
                            passwordEncoder.encode("admin"),
                            Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_ID)),
                            ChannelType.ACCOUNT,
                            true,
                            true,
                            true,
                            true,
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

    @Override
    public UserDetails registerUser(@NonNull AuthUser authUser,
                                    @NonNull String username,
                                    @NonNull String defaultAuthority,
                                    @Nullable String decodeState) throws RegisterUserFailureException {
        // TODO

        // 示例：
        log.info("Demo ======>: 注册用户名：{}, 第三方登录注册成功", username);
        return new ArchUser(username,
                            passwordEncoder.encode(authUser.getToken().getAccessToken()),
                            Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_ID)),
                            ChannelType.OAUTH2,
                            true,
                            true,
                            true,
                            true,
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
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
        // TODO 测试
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < usernames.length; i++) {
            result.add(Boolean.TRUE);
        }
        return result;
    }
}
