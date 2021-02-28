package org.arch.auth.sso.jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.utils.IpUtils;
import org.arch.framework.ums.enums.ScopesType;
import org.arch.framework.ums.properties.AuthClientScopesCacheProperties;
import org.arch.ums.account.vo.AuthClientVo;
import org.arch.ums.feign.account.client.UmsAccountAuthClientFeignService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.dcenter.ums.security.common.api.tasks.handler.JobHandler;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkEndpointPermissionService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.ums.consts.ClientConstants.CLIENT_ID_HEADER_NAME;
import static org.arch.framework.ums.consts.ClientConstants.CLIENT_SECRET_HEADER_NAME;

/**
 * jws set uri 访问权限访问服务接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 17:33
 */
@Service
@Slf4j
public class ArchJwkEndpointPermissionServiceImpl implements JwkEndpointPermissionService, InitializingBean, JobHandler {

    private final UmsAccountAuthClientFeignService umsAccountAuthClientFeignService;
    private final TenantContextHolder tenantContextHolder;
    private final RedisConnectionFactory redisConnectionFactory;
    private final AuthClientScopesCacheProperties authClientScopesCacheProperties;
    private final String mac;
    /**
     * Map(tenantId, Map(ClientId, AuthClientVo))
     */
    private volatile Map<Integer, Map<String, AuthClientVo>> allScopeMap;

    public ArchJwkEndpointPermissionServiceImpl(UmsAccountAuthClientFeignService umsAccountAuthClientFeignService,
                                                TenantContextHolder tenantContextHolder,
                                                RedisConnectionFactory redisConnectionFactory,
                                                AuthClientScopesCacheProperties authClientScopesCacheProperties) {
        this.umsAccountAuthClientFeignService = umsAccountAuthClientFeignService;
        this.tenantContextHolder = tenantContextHolder;
        this.redisConnectionFactory = redisConnectionFactory;
        this.authClientScopesCacheProperties = authClientScopesCacheProperties;
        // 集群多台服务器获取本机缓存可能出错(极小极小概率)而获取同一默认值的问题.
        this.mac = IpUtils.getMACAddress();
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
            connection.hSet(authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashKey()
                                                                                .getBytes(StandardCharsets.UTF_8),
                                                 authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashField()
                                                                                .getBytes(StandardCharsets.UTF_8),
                                                 "1".getBytes(StandardCharsets.UTF_8));

            List<byte[]> list = connection.hMGet(authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashKey()
                                                                                .getBytes(StandardCharsets.UTF_8),
                                                 authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashField()
                                                                                .getBytes(StandardCharsets.UTF_8),
                                                 mac.getBytes(StandardCharsets.UTF_8));
            if (isNull(list) || isNull(list.get(0))) {
                // hGet "AUTH:CLIENT:SCOPES:UPDATED" "ALL" 没有值, 不需要更新本地缓存
                return;
            }

            // hGet "AUTH:CLIENT:SCOPES:UPDATED" mac 没有值, 需要更新本地缓存
            if (isNull(list.get(1))) {
                // 对 scopes 进行本地缓存.
                syncToLocalCache();
                // 标记本地缓存已经更新.
                connection.hSet(authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashKey()
                                                               .getBytes(StandardCharsets.UTF_8),
                                authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashField()
                                                               .getBytes(StandardCharsets.UTF_8),
                                "1".getBytes(StandardCharsets.UTF_8));
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
        // 对 scopes 进行本地缓存.
        final Response<Map<Integer, Map<String, AuthClientVo>>> response = umsAccountAuthClientFeignService.getAllScopes();
        Map<Integer, Map<String, AuthClientVo>> allScopeMap = response.getSuccessData();
        if (nonNull(allScopeMap)) {
        	this.allScopeMap = allScopeMap;
        }
    }
}
