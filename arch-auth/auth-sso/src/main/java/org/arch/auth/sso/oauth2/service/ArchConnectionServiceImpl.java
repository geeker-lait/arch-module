package org.arch.auth.sso.oauth2.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.arch.auth.sso.oauth2.dto.ConnectionDataDto;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.api.IdKey;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.feign.account.client.UmsAccountAuthTokenFeignService;
import org.arch.ums.feign.account.client.UmsAccountIdentifierFeignService;
import org.arch.ums.feign.exception.FeignCallException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.common.utils.JsonUtil;
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
import java.util.Objects;

import static java.util.Objects.isNull;
import static org.arch.auth.sso.utils.RegisterUtils.getTraceId;
import static org.arch.auth.sso.utils.RegisterUtils.toOauthToken;
import static org.arch.framework.beans.utils.RetryUtils.publishRetryEvent;
import static org.arch.framework.ums.enums.ChannelType.OAUTH2;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * arch 第三方登录时, 自动注册, 更新第三方用户信息, 绑定第三方用户到本地的服务实现..
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.9 17:25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArchConnectionServiceImpl implements ConnectionService, ApplicationContextAware {

    private final UmsAccountIdentifierFeignService umsAccountIdentifierFeignService;
    private final UmsUserDetailsService umsUserDetailsService;
    private final SsoProperties ssoProperties;
    private final TenantContextHolder tenantContextHolder;
    private final PasswordEncoder passwordEncoder;
    private final IdService idService;
    private final Auth2Properties auth2Properties;
    private final UmsAccountAuthTokenFeignService umsAccountAuthTokenFeignService;
    private ApplicationContext applicationContext;

    @NonNull
    @Override
    public UserDetails signUp(@NonNull AuthUser authUser, @NonNull String providerId, @NonNull String encodeState) throws RegisterUserFailureException {

        // 这里为第三方登录自动注册时调用，所以这里不需要实现对用户信息的注册，可以在用户登录完成后提示用户修改用户信息。
        try {
            String[] usernames = umsUserDetailsService.generateUsernames(authUser);
            // 1. 重名检查
            String username = null;
            final List<Boolean> existedByUserIds = umsUserDetailsService.existedByUsernames(usernames);
            for(int i = 0, len = existedByUserIds.size(); i < len; i++) {
                if (!existedByUserIds.get(i))
                {
                    username = usernames[i];
                    break;
                }
            }
            // 2. 用户重名, 自动注册失败
            if (username == null)
            {
                throw new RegisterUserFailureException(ErrorCodeEnum.USERNAME_USED, authUser.getUsername());
            }

            // 解密 encodeState  目前不需要

            // 3. 注册到本地账户
            String tenantId = tenantContextHolder.getTenantId();
            String defaultAuthorities = RegisterUtils.getDefaultAuthorities(ssoProperties, tenantId);
            ArchUser archUser = (ArchUser) umsUserDetailsService.registerUser(authUser, username,
                                                                              defaultAuthorities, encodeState);

            // 4. 保存第三方用户的 OauthToken 信息
            int timeout = auth2Properties.getProxy().getHttpConfig().getTimeout();
            OauthToken oauthToken = toOauthToken(authUser, tenantId,
                                        archUser.getIdentifierId(), timeout);
            try {
                umsAccountAuthTokenFeignService.save(oauthToken);
            }
            catch (Exception e) {
                log.error(e.getMessage(),e);
                saveOrUpdateOauthToken(oauthToken, "保存第三方授权登录用户信息失败, 发布重试事件", "save");
            }
            return archUser;
        }
        catch (Exception e) {
            String source = authUser.getSource();
            log.error(String.format("OAuth2自动注册失败: error=%s, provider=%s, authUser=%s",
                                    e.getMessage(), source, JsonUtil.toJsonString(authUser)), e);
            throw new RegisterUserFailureException(ErrorCodeEnum.USER_REGISTER_FAILURE, source);
        }

    }

    @Override
    public void updateUserConnectionAndAuthToken(AuthUser authUser, ConnectionData connectionData) throws UpdateConnectionException {
        // 因 第三方登录记录与账号为同一张表, 所以省略 UserConnection 的更新.

        try {
            // 更新 OAuthToken
            int timeout = auth2Properties.getProxy().getHttpConfig().getTimeout();
            // 1. connectionData 是 ConnectionDataDto 的情况
            if ((connectionData instanceof ConnectionDataDto)) {
                ConnectionDataDto connectionDataDto = (ConnectionDataDto) connectionData;
                OauthToken oauthToken = toOauthToken(authUser,
                                                     tenantContextHolder.getTenantId(),
                                                     connectionDataDto.getIdentifierId(),
                                                     timeout);
                updateByIdentifierId(oauthToken);
                return;
            }

            // 2. connectionData 不是 ConnectionDataDto 的情况
            try {
                // 2.1 已登录的情况
                TokenInfo currentUser = SecurityUtils.getCurrentUser();
                OauthToken oauthToken = toOauthToken(authUser,
                                                     tenantContextHolder.getTenantId(),
                                                     currentUser.getIdentifierId(),
                                                     timeout);
                updateByIdentifierId(oauthToken);
            }
            catch (AuthenticationException e) {
                // 2.2 未登录的情况
                String[] usernames = umsUserDetailsService.generateUsernames(authUser);
                ArchUser archUser = (ArchUser) umsUserDetailsService.loadUserByUserId(usernames[0]);
                OauthToken oauthToken = toOauthToken(authUser,
                                                     tenantContextHolder.getTenantId(),
                                                     archUser.getIdentifierId(),
                                                     timeout);
                updateByIdentifierId(oauthToken);
            }

        }
        catch (FeignException e) {
            String source = authUser.getSource();
            log.error(String.format("更新第三方授权登录用户信息失败: error=%s, provider=%s, authUser=%s",
                                    e.getMessage(), source, JsonUtil.toJsonString(authUser)), e);
            throw new UpdateConnectionException(ErrorCodeEnum.UPDATE_CONNECTION_DATA_FAILURE, source);
        }

    }

    @Nullable
    @Override
    public List<ConnectionData> findConnectionByProviderIdAndProviderUserId(@NonNull String providerId,
                                                                            @NonNull String providerUserId) {
        try {
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
        catch (FeignException e) {
            String msg = String.format("获取第三方登录信息失败: providerId=%s, providerUserId=%s",
                                          providerId, providerUserId);
            throw new FeignCallException(ResponseStatusCode.FAILED, null, msg, e);
        }
    }

    /**
     * 解除绑定(第三方)
     * @param userId            解绑的用户 Id, 即 {@link Identifier#getIdentifier()}
     * @param providerId        第三方服务商 Id
     * @param providerUserId    第三方用户 Id
     */
    @Override
    public void unbinding(@NonNull String userId, @NonNull String providerId, @NonNull String providerUserId) {

        try {
            // 1. 获取并检查 userId 对应的 Identifier 相关信息.
            TokenInfo currentUser = SecurityUtils.getCurrentUser();
            if (currentUser.getAccountName().equals(userId)) {
                log.debug("用户 {} 进行解绑操作时, 不能解绑的已登录的账号; providerId: {}, providerUserId: {}",
                          userId, providerId, providerUserId);
                throw new UnBindingException(ErrorCodeEnum.UN_BINDING_LOGGED_ERROR, userId);
            }
            Identifier identifierRequest = new Identifier();
            identifierRequest.setAid(currentUser.getAccountId());
            identifierRequest.setTenantId(currentUser.getTenantId());
            Response<List<Identifier>> listResponse = umsAccountIdentifierFeignService.find(identifierRequest);
            List<Identifier> identifierList = listResponse.getSuccessData();
            if (isEmpty(identifierList)) {
                log.debug("用户 {} 进行解绑操作时, 获取用户信息失败; providerId: {}, providerUserId: {}",
                         userId, providerId, providerUserId);
                throw new UnBindingException(ErrorCodeEnum.UN_BINDING_ERROR, userId);
            }
            // 检查 userId 是否在 identifierList 中
            Identifier unbindingIdentifier = null;
            for (Identifier identifier : identifierList) {
                if (identifier.getIdentifier().equals(userId)) {
                    unbindingIdentifier = identifier;
                    break;
                }
            }
            if (isNull(unbindingIdentifier)) {
                log.debug("用户 {} 进行解绑操作时, 不能解绑的不属于自己的第三方账号; providerId: {}, providerUserId: {}",
                          userId, providerId, providerUserId);
                throw new UnBindingException(ErrorCodeEnum.UN_BINDING_ILLEGAL, userId);
            }
            if (Objects.equals(unbindingIdentifier.getChannelType(), OAUTH2)) {
                log.debug("用户 {} 进行解绑操作时, 只能解绑第三方的账号; providerId: {}, providerUserId: {}",
                          userId, providerId, providerUserId);
                throw new UnBindingException(ErrorCodeEnum.UN_BINDING_NOT_OAUTH2, userId);
            }
            if (identifierList.size() == 1) {
                log.debug("用户 {} 进行解绑操作时, 不支持解绑自己; providerId: {}, providerUserId: {}",
                          userId, providerId, providerUserId);
                throw new UnBindingException(ErrorCodeEnum.UN_BINDING_YOURSELF_ERROR, userId);
            }

            // 2. 解绑
            Response<Boolean> response = umsAccountIdentifierFeignService.unbinding(unbindingIdentifier.getAid(),
                                                                                    unbindingIdentifier.getIdentifier());
            Boolean successData = response.getSuccessData();
            if (isNull(successData) || !successData) {
                throw new UnBindingException(ErrorCodeEnum.UN_BINDING_ERROR, userId);
            }
        }
        catch (FeignException e) {
            String msg = String.format("用户解绑失败: userId=%s, providerId=%s, providerUserId=%s",
                                       userId, providerId, providerUserId);
            throw new FeignCallException(ResponseStatusCode.FAILED, null, msg, e);
        }
    }

    @Override
    public void binding(@NonNull UserDetails principal, @NonNull AuthUser authUser, @NonNull String providerId) {

        try {
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
                    .setChannelType(OAUTH2);
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
            OauthToken oauthToken = toOauthToken(authUser,
                                                 tenantContextHolder.getTenantId(),
                                                 identifierRequest.getId(),
                                                 timeout);
            try {
                umsAccountAuthTokenFeignService.save(oauthToken);
            }
            catch (Exception e) {
                log.error(e.getMessage(),e);
                saveOrUpdateOauthToken(oauthToken, "保存第三方授权登录用户信息失败, 发布重试事件", "save");
            }

        }
        catch (FeignException e) {
            String msg = String.format("用户绑定失败: userId=%s, providerId=%s, providerUserId=%s",
                                       principal.getUsername(), providerId, authUser.getUuid());
            throw new FeignCallException(ResponseStatusCode.FAILED, null, msg, e);
        }

    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void updateByIdentifierId(@NonNull OauthToken oauthToken) {
        try {
            Response<Boolean> response = umsAccountAuthTokenFeignService.updateByIdentifierId(oauthToken);
            Boolean successData = response.getSuccessData();
            if (isNull(successData) || !successData) {
                saveOrUpdateOauthToken(oauthToken, "更新第三方授权登录用户信息失败, 发布重试事件", "updateByIdentifierId");
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            saveOrUpdateOauthToken(oauthToken, "更新第三方授权登录用户信息失败, 发布重试事件", "updateByIdentifierId");
        }
    }

    private void saveOrUpdateOauthToken(@NonNull OauthToken oauthToken, @NonNull String errorMsg, @NonNull String methodName) {
        log.warn(errorMsg);
        publishRetryEvent(this.applicationContext, getTraceId(),
                          this.umsAccountAuthTokenFeignService,
                          UmsAccountAuthTokenFeignService.class,
                          methodName,
                          new Class[]{OauthToken.class},
                          oauthToken);
    }

}
