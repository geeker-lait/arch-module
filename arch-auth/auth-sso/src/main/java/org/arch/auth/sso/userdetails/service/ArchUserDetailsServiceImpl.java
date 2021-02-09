package org.arch.auth.sso.userdetails.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.auth.sso.request.bind.RegRequest;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.api.IdKey;
import org.arch.framework.beans.Response;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.enums.ChannelType;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.feign.account.client.UmsAccountClient;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;
import top.dcenter.ums.security.core.exception.UserNotExistException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 用户登录与注册服务实现
 * @author YongWu zheng
 * @since 2021.1.3 11:42
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ArchUserDetailsServiceImpl implements UmsUserDetailsService {

    /**
     * 用于用户名密码注册时在 request 中传递 {@link RegRequest} 参数的参数名称.
     */
    public static final String REG_REQUEST_PARAMETER_NAME = "regRequest";

    /**
     * 用于密码加解密
     */
    private final PasswordEncoder passwordEncoder;
    private final IdService idService;
    private final SsoProperties ssoProperties;
    private final TenantContextHolder tenantContextHolder;
    private final UmsAccountClient umsAccountClient;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return loadUserByUserId(username);
    }

    @NonNull
    @Override
    public UserDetails loadUserByUserId(@NonNull String userId) throws UsernameNotFoundException {
        try {
            // 根据用户名获取用户信息
            AuthLoginDto authLoginDto;
            final Response<AuthLoginDto> response = umsAccountClient.loadAccountByIdentifier(userId);
            authLoginDto = response.getSuccessData();

            if (isNull(authLoginDto)) {
                throw new UsernameNotFoundException(userId + " not found");
            }

            final ArchUser archUser = new ArchUser(authLoginDto.getIdentifier(),
                                                   authLoginDto.getCredential(),
                                                   authLoginDto.getAid(),
                                                   authLoginDto.getTenantId(),
                                                   authLoginDto.getChannelType(),
                                                   authLoginDto.getNickName(),
                                                   authLoginDto.getAvatar(),
                                                   true,
                                                   true,
                                                   true,
                                                   true,
                                                   AuthorityUtils.commaSeparatedStringToAuthorityList(authLoginDto.getAuthorities()));
            if (log.isInfoEnabled()) {
                log.info("用户登录成功: 租户: {}, 登录用户名: {}, aid: {}",
                         authLoginDto.getTenantId(), userId, authLoginDto.getAid());
            }
            return archUser;

        }
        catch (UsernameNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            String msg = String.format("登录失败: 登录用户名：%s, 失败信息: %s", userId, e.getMessage());
            log.error(msg);
            throw new UserNotExistException(ErrorCodeEnum.QUERY_USER_INFO_ERROR, e, userId);
        }
    }

    /**
     * 用户手机注册接口实现
     * @param mobile    手机号
     * @return  {@link ArchUser}
     * @throws RegisterUserFailureException 注册失败
     */
    @NonNull
    @Override
    public UserDetails registerUser(@NonNull String mobile) throws RegisterUserFailureException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (isNull(requestAttributes)) {
            throw new RegisterUserFailureException(ErrorCodeEnum.USER_REGISTER_FAILURE, mobile);
        }
        // 获取注册的账户类型
        AccountType accountType = RegisterUtils.getAccountType(ssoProperties.getAccountTypeParameterName());
        if (isNull(accountType)) {
            log.warn("用户注册失败, accountType 没有传递 或 格式错误");
            throw new RegisterUserFailureException(ErrorCodeEnum.USER_REGISTER_FAILURE, mobile);
        }

        // 注册
        AuthRegRequest authRegRequest = getMobileRegRequest(mobile, accountType);
        return registerUser(authRegRequest);
    }

    /**
     * 用户密码注册接口实现
     * @param request   {@link ServletWebRequest}
     * @return  {@link ArchUser}
     * @throws RegisterUserFailureException 注册失败
     */
    @NonNull
    @Override
    public UserDetails registerUser(@NonNull ServletWebRequest request) throws RegisterUserFailureException {
        try {

            RegRequest regRequest =
                    (RegRequest) request.getAttribute(REG_REQUEST_PARAMETER_NAME, RequestAttributes.SCOPE_REQUEST);
            if (isNull(regRequest)) {
                throw new RegisterUserFailureException(ErrorCodeEnum.USER_REGISTER_FAILURE, null);
            }

            // 获取注册的账户类型
            AccountType accountType = RegisterUtils.getAccountType(ssoProperties.getAccountTypeParameterName());
            if (isNull(accountType)) {
                log.warn("用户注册失败, accountType 没有传递 或 格式错误");
                throw new RegisterUserFailureException(ErrorCodeEnum.USER_REGISTER_FAILURE, regRequest.getUsername());
            }

            // 查询用户是否已被注册
            List<Boolean> exists = existedByUsernames(regRequest.getUsername());
            if (exists.get(0)) {
                throw new RegisterUserFailureException(ErrorCodeEnum.USERNAME_USED, regRequest.getUsername());
            }

            // 用户注册
            AuthRegRequest authRegRequest = getAuthRegRequest(regRequest, accountType);
            return registerUser(authRegRequest);

        }
        catch (Exception e) {
            throw new RegisterUserFailureException(ErrorCodeEnum.USERNAME_USED, e, null);
        }
    }

    /**
     * 第三方登录注册接口实现
     * @param authUser          第三方登录用户信息
     * @param username          账号标识({@link Identifier#getIdentifier()})
     * @param defaultAuthority  默认权限
     * @param decodeState       第三方授权登录时 state
     * @return  {@link ArchUser}
     * @throws RegisterUserFailureException 注册失败
     */
    @NonNull
    @Override
    public UserDetails registerUser(@NonNull AuthUser authUser,
                                    @NonNull String username,
                                    @NonNull String defaultAuthority,
                                    @Nullable String decodeState) throws RegisterUserFailureException {
        // TODO

        // 示例：
        log.info("Demo ======>: 注册用户名：{}, 第三方登录注册成功", username);
        String tenantId = tenantContextHolder.getTenantId();
        return new ArchUser(username,
                            passwordEncoder.encode(authUser.getToken().getAccessToken()),
                            Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_ID)),
                            Integer.valueOf(tenantId),
                            ChannelType.OAUTH2,
                            "admin",
                            ssoProperties.getDefaultAvatar(),
                            true,
                            true,
                            true,
                            true,
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,TENANT_" + tenantId));
    }

    /**
     * 第三方授权登录注册时生成 账号标识({@link Identifier#getIdentifier()}) 接口
     * @param authUser  第三方用户信息
     * @return  账号标识({@link Identifier#getIdentifier()})
     */
    @NonNull
    @Override
    public String[] generateUsernames(@NonNull AuthUser authUser) {
        /*
         第三方服务商 providerId = authUser.getSource()
         第三方系统的唯一id: authUser.getUuid()
         用户名: authUser.getUsername()
        */
        return new String[]{
                authUser.getSource() + "_" + authUser.getUuid(),
        };
    }

    @NonNull
    @Override
    public List<Boolean> existedByUsernames(String... usernames) throws IOException {

        List<Boolean> result = new ArrayList<>();
        int length = usernames.length;

        if (length == 0) {
            return result;
        }

        List<String> usernameList = Arrays.stream(usernames).collect(Collectors.toList());
        Response<List<Boolean>> response = umsAccountClient.exists(usernameList);
        List<Boolean> successData = response.getSuccessData();
        if (nonNull(successData)) {
        	return successData;
        }
        throw new IOException("查询数据库时发生异常");
    }

    @NonNull
    private ArchUser registerUser(@NonNull AuthRegRequest authRegRequest) {
        Response<AuthLoginDto> response = umsAccountClient.register(authRegRequest);
        AuthLoginDto authLoginDto = response.getSuccessData();
        if (isNull(authLoginDto)) {
            log.warn("用户注册失败: {}", response.getMsg());
            throw new RegisterUserFailureException(ErrorCodeEnum.USERNAME_USED, authRegRequest.getIdentifier());
        }

        // 用户注册成功转换为 UserDetails
        final ArchUser archUser = new ArchUser(authLoginDto.getIdentifier(),
                                               authLoginDto.getCredential(),
                                               authLoginDto.getAid(),
                                               authLoginDto.getTenantId(),
                                               authLoginDto.getChannelType(),
                                               authLoginDto.getNickName(),
                                               authLoginDto.getAvatar(),
                                               true,
                                               true,
                                               true,
                                               true,
                                               AuthorityUtils.commaSeparatedStringToAuthorityList(
                                                       authLoginDto.getAuthorities()));
        if (log.isInfoEnabled()) {
            log.info("用户注册成功: 租户: {}, 注册用户名: {}, aid: {}, channelType: {}, source: {}",
                     archUser.getTenantId(), archUser.getUsername(), archUser.getAccountId(),
                     archUser.getChannelType().name(), authRegRequest.getSource());
        }
        return archUser;
    }

    @NonNull
    private AuthRegRequest getMobileRegRequest(@NonNull String mobile, @NonNull AccountType accountType) {
        // 获取推荐信息
        String source = RegisterUtils.getSource(ssoProperties.getSourceParameterName());
        // 获取租户 ID
        String tenantId = tenantContextHolder.getTenantId();
        // 构建默认的用户权限
        String authorities = RegisterUtils.getDefaultAuthorities(ssoProperties, tenantId);
        // 构建注册参数
        return AuthRegRequest.builder()
                             .aid(Long.valueOf(idService.generateId(accountType.getIdKey())))
                             .tenantId(Integer.valueOf(tenantId))
                             .identifier(mobile)
                             .credential(passwordEncoder.encode(ssoProperties.getDefaultPassword()))
                             .authorities(authorities)
                             .avatar(ssoProperties.getDefaultAvatar())
                             .channelType(ChannelType.PHONE)
                             .nickName(mobile)
                             .source(source)
                             .build();
    }

    private AuthRegRequest getAuthRegRequest(RegRequest regRequest, AccountType accountType) {
        // 获取头像
        String avatar = regRequest.getAvatar();
        if (!hasText(avatar)) {
            avatar = ssoProperties.getDefaultAvatar();
        }
        // 获取推荐信息
        String source = RegisterUtils.getSource(ssoProperties.getSourceParameterName());
        // 获取租户 ID
        String tenantId = tenantContextHolder.getTenantId();
        // 构建默认的用户权限
        String authorities = RegisterUtils.getDefaultAuthorities(ssoProperties, tenantId);


        return AuthRegRequest.builder()
                             .aid(Long.valueOf(idService.generateId(accountType.getIdKey())))
                             .tenantId(Integer.valueOf(tenantId))
                             .identifier(regRequest.getUsername())
                             .credential(passwordEncoder.encode(regRequest.getPassword()))
                             .authorities(authorities)
                             .avatar(avatar)
                             .channelType(ChannelType.ACCOUNT)
                             .nickName(regRequest.getNickName())
                             .source(source)
                             .build();
    }


}
