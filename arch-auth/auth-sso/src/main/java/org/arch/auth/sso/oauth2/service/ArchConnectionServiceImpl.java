package org.arch.auth.sso.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.arch.auth.sso.oauth2.dto.ConnectionDataDto;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.api.IdKey;
import org.arch.framework.beans.Response;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.enums.ChannelType;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService;
import org.arch.ums.feign.account.client.UmsAccountIdentifierFeignService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.oauth.entity.ConnectionData;
import top.dcenter.ums.security.core.api.oauth.repository.exception.UpdateConnectionException;
import top.dcenter.ums.security.core.api.oauth.signup.ConnectionService;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.BindingException;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;
import top.dcenter.ums.security.core.exception.UnBindingException;
import top.dcenter.ums.security.core.exception.UserNotExistException;
import top.dcenter.ums.security.core.oauth.properties.Auth2Properties;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static org.arch.auth.sso.utils.RegisterUtils.toOauthToken;

/**
 * arch 第三方登录时, 自动注册, 更新第三方用户信息, 绑定第三方用户到本地的服务实现..
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.9 17:25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArchConnectionServiceImpl implements ConnectionService {

    private final UmsAccountIdentifierFeignService umsAccountIdentifierFeignService;
    private final UmsUserDetailsService umsUserDetailsService;
    private final SsoProperties ssoProperties;
    private final TenantContextHolder tenantContextHolder;
    private final PasswordEncoder passwordEncoder;
    private final IdService idService;
    private final Auth2Properties auth2Properties;
    private final UmsAccountAuthTokenFeignService umsAccountAuthTokenFeignService;

    @NonNull
    @Override
    public UserDetails signUp(@NonNull AuthUser authUser, @NonNull String providerId, @NonNull String encodeState) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public void updateUserConnectionAndAuthToken(AuthUser authUser, ConnectionData connectionData) throws UpdateConnectionException {
        // todo

    }

    @Nullable
    @Override
    public List<ConnectionData> findConnectionByProviderIdAndProviderUserId(@NonNull String providerId,
                                                                            @NonNull String providerUserId) {
        String identifier = RegisterUtils.getIdentifierForOauth2(providerId, providerUserId);
        Response<AuthLoginDto> response = umsAccountIdentifierFeignService.loadAccountByIdentifier(identifier);
        final AuthLoginDto authLoginDto = response.getSuccessData();
        if (isNull(authLoginDto)) {
            return null;
        }

        ConnectionDataDto connectionDataDto = new ConnectionDataDto();
        connectionDataDto.setIdentifierId(authLoginDto.getId());
        connectionDataDto.setUserId(authLoginDto.getIdentifier());
        connectionDataDto.setProviderId(providerId);
        connectionDataDto.setProviderUserId(providerUserId);
        connectionDataDto.setDisplayName(authLoginDto.getNickName());
        connectionDataDto.setImageUrl(authLoginDto.getAvatar());

        return Collections.singletonList(connectionDataDto);
    }

    /**
     * 解除绑定(第三方)
     * @param userId            用户 Id, 即 {@link Identifier#getIdentifier()}
     * @param providerId        第三方服务商 Id
     * @param providerUserId    第三方用户 Id
     */
    @Override
    public void unbinding(@NonNull String userId, @NonNull String providerId, @NonNull String providerUserId) {

        // 1. 获取 userId 对应的 Identifier 相关信息.
        Response<AuthLoginDto> authLoginDtoResponse = umsAccountIdentifierFeignService.loadAccountByIdentifier(userId);
        AuthLoginDto authLoginDto = authLoginDtoResponse.getSuccessData();
        if (isNull(authLoginDto)) {
            log.warn("用户 {} 进行解绑操作时, 获取用户信息失败; providerId: {}, providerUserId: {}",
                     userId, providerId, providerUserId);
            throw new UnBindingException(ErrorCodeEnum.UN_BINDING_ERROR, userId);
        }

        // 2. 解绑
        String identifier = RegisterUtils.getIdentifierForOauth2(providerId, providerUserId);
        Response<Boolean> response = umsAccountIdentifierFeignService.unbinding(authLoginDto.getAid(), identifier);
        Boolean successData = response.getSuccessData();
        if (isNull(successData) || !successData) {
            throw new UnBindingException(ErrorCodeEnum.UN_BINDING_ERROR, userId);
        }
    }

    @Override
    public void binding(@NonNull UserDetails principal, @NonNull AuthUser authUser, @NonNull String providerId) {

        // 1. 获取 principal 对应的 Identifier 相关信息.
        ArchUser archUser = (ArchUser) principal;
        Response<AuthLoginDto> authLoginDtoResponse = umsAccountIdentifierFeignService.loadAccountByIdentifier(archUser.getUsername());
        AuthLoginDto authLoginDto = authLoginDtoResponse.getSuccessData();
        if (isNull(authLoginDto)) {
            log.warn("用户 {} 进行绑定操作时, 获取用户信息失败; providerId: {}, providerUserId: {}",
                     principal.getUsername(), providerId, authUser.getUuid());
            throw new BindingException(ErrorCodeEnum.BINDING_ERROR, principal.getUsername());
        }

        // 2. 检查 authUser 是否已经绑定
        // 获取 authUser 对应的 Identifier 账号标识
        String[] usernames = umsUserDetailsService.generateUsernames(authUser);
        String identifier = usernames[0];
        try {
            ArchUser oAuth2UserDetails = (ArchUser) umsUserDetailsService.loadUserByUsername(identifier);
            if (authLoginDto.getAid().equals(oAuth2UserDetails.getAccountId())) {
                // 用户已经绑定在 principal 上
                return;
            }
            // authUser 已经绑定在其他账号上
            throw new BindingException(ErrorCodeEnum.BOUND_ERROR, principal.getUsername());
        }
        catch (UserNotExistException e) {
            String msg = String.format("用户 %s 进行绑定操作时, 查询第三方是否已经绑定失败; providerId: %s, providerUserId: %s",
                                       principal.getUsername(), providerId, authUser.getUuid());
            log.error(msg, e);
        }
        catch (UsernameNotFoundException e) {
            // 用户不存在, 继续执行绑定操作
        }

        // 3. 第三方 authUser 绑定到 principal 对应的 Identifier 账号上
        // 获取租户 ID
        String tenantId = tenantContextHolder.getTenantId();
        // 构建默认的用户权限
        String authorities = RegisterUtils.getDefaultAuthorities(ssoProperties, tenantId);
        // 构建绑定请求参数
        Identifier identifierRequest = new Identifier()
                .setId(Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_IDENTIFIER_ID)))
                .setAid(authLoginDto.getAid())
                .setTenantId(Integer.valueOf(tenantId))
                .setIdentifier(identifier)
                .setCredential(passwordEncoder.encode(ssoProperties.getDefaultPassword()))
                .setAuthorities(authorities)
                .setChannelType(ChannelType.OAUTH2);
        // 绑定
        try {
            umsAccountIdentifierFeignService.save(identifierRequest);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BindingException(ErrorCodeEnum.BINDING_ERROR, principal.getUsername());
        }

        // 4. 保存第三方用户的 OauthToken 信息
        int timeout = auth2Properties.getProxy().getHttpConfig().getTimeout();
        umsAccountAuthTokenFeignService.save(toOauthToken(authUser,
                                                          tenantContextHolder.getTenantId(),
                                                          identifierRequest.getId(),
                                                          timeout));

    }

}
