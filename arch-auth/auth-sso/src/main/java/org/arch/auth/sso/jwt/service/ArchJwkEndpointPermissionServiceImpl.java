package org.arch.auth.sso.jwt.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;
import org.arch.framework.ums.enums.ScopesType;
import org.arch.framework.ums.properties.AuthClientScopesCacheProperties;
import org.arch.ums.account.vo.AuthClientVo;
import org.arch.ums.account.client.AccountAuthClientFeignService;
import org.arch.framework.feign.exception.FeignCallException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.dcenter.ums.security.common.api.tasks.handler.JobHandler;
import top.dcenter.ums.security.common.utils.UuidUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.ums.consts.ClientConstants.CLIENT_ID_HEADER_NAME;
import static org.arch.framework.ums.consts.ClientConstants.CLIENT_SECRET_HEADER_NAME;
import static org.arch.framework.utils.AuthClientSyncUtils.checkScopesUpdate;
import static org.arch.framework.utils.AuthClientSyncUtils.setScopesLocalUpdateSyncFlag;

/**
 * jws set uri 访问权限访问服务接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 17:33
 */
@Service
@Slf4j
public class ArchJwkEndpointPermissionServiceImpl implements JwkEndpointPermissionService, InitializingBean, JobHandler {

    private final AccountAuthClientFeignService accountAuthClientFeignService;
    private final TenantContextHolder tenantContextHolder;
    private final RedisConnectionFactory redisConnectionFactory;
    private final AuthClientScopesCacheProperties authClientScopesCacheProperties;
    /**
     * 代表微服务的唯一标识
     */
    private final String scopeUpdateUuid;
    /**
     * Map(tenantId, Map(ClientId, AuthClientVo))
     */
    private volatile Map<Integer, Map<String, AuthClientVo>> allScopeMap;

    public ArchJwkEndpointPermissionServiceImpl(AccountAuthClientFeignService accountAuthClientFeignService,
                                                TenantContextHolder tenantContextHolder,
                                                RedisConnectionFactory redisConnectionFactory,
                                                AuthClientScopesCacheProperties authClientScopesCacheProperties) {
        this.accountAuthClientFeignService = accountAuthClientFeignService;
        this.tenantContextHolder = tenantContextHolder;
        this.redisConnectionFactory = redisConnectionFactory;
        this.authClientScopesCacheProperties = authClientScopesCacheProperties;
        this.scopeUpdateUuid = UuidUtils.getUUID();
    }

    @NonNull
    @Override
    public Boolean hasPermission(@NonNull HttpServletRequest request) {
        // 检查是否有访问 jws set uri 的访问权限, 权限存储在 AccountOauthClient 的 scopes 字段
        return hasPermissionOfApp(request);
    }

    /**
     * 检查是否有访问 jws set uri 的访问权限, 即 AccountOauthClient 的 clientId 所对应的
     * scopes 字段(JWK,USER,UMS,..)中是否有 {@link ScopesType#JWK}.
     * @param request     {@link HttpServletRequest}
     * @return  返回 true 表示有访问权限.
     */
    @NonNull
    private Boolean hasPermissionOfApp(@NonNull HttpServletRequest request) {
        Set<String> scopeSet = getScopes(request);
        return scopeSet.contains(ScopesType.JWK.name());
    }

    @NonNull
    private Set<String> getScopes(@NonNull HttpServletRequest request) {
        String clientId = request.getHeader(CLIENT_ID_HEADER_NAME);
        String clientSecret = request.getHeader(CLIENT_SECRET_HEADER_NAME);
        String tenantId = tenantContextHolder.getTenantId();
        // 从本地缓存中获取 scopes .
        if (isNull(allScopeMap)) {
        	syncToLocalCache();
        }
        Map<String, AuthClientVo> stringAuthClientVoMap = allScopeMap.get(Integer.valueOf(tenantId));
        if (isNull(stringAuthClientVoMap)) {
        	return Collections.emptySet();
        }

        AuthClientVo authClientVo = stringAuthClientVoMap.get(clientId);
        if (isNull(authClientVo)) {
        	return Collections.emptySet();
        }

        if (authClientVo.getClientSecret().equals(clientSecret)) {
            return StringUtils.commaDelimitedListToSet(authClientVo.getScopes());
        }

        return Collections.emptySet();
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void afterPropertiesSet() throws Exception {
        // 对 scopes 进行本地缓存.
        try {
            syncToLocalCache();
        }
        catch (Exception e) {
            log.warn("服务器启动同步 AuthClient 的 scopes 缓存未成功");
        }
    }

    @Override
    public void run() {
        try (RedisConnection connection = getConnection()) {
            if (checkScopesUpdate(this.authClientScopesCacheProperties,
                                  connection,
                                  this.scopeUpdateUuid)) {
                // 对 scopes 进行本地缓存.
                syncToLocalCache();
                // 标记本地缓存已经更新.
                setScopesLocalUpdateSyncFlag(this.authClientScopesCacheProperties,
                                             connection,
                                             this.scopeUpdateUuid);
            }
        }
    }

    @Override
    public String cronExp() {
        return authClientScopesCacheProperties.getCronExp();
    }

    private RedisConnection getConnection() {
        return redisConnectionFactory.getConnection();
    }

    private void syncToLocalCache() {
        try {
            // 对 scopes 进行本地缓存. Map(tenantId, Map(clientId, AuthClientVo))
            final Response<Map<Integer, Map<String, AuthClientVo>>> response = accountAuthClientFeignService.getAllScopes();
            Map<Integer, Map<String, AuthClientVo>> allScopeMap = response.getSuccessData();
            if (nonNull(allScopeMap)) {
                this.allScopeMap = allScopeMap;
            }
        }
        catch (FeignException e) {
            String msg = "scopes 本地缓存失败";
            throw new FeignCallException(ResponseStatusCode.FAILED, null, msg, e);
        }
    }
}
