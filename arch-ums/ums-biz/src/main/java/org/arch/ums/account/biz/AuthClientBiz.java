package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.consts.RoleConstants;
import org.arch.framework.ums.properties.AuthClientScopesCacheProperties;
import org.arch.framework.utils.AuthClientSyncUtils;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.AuthClientRequest;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.rest.AuthClientRest;
import org.arch.ums.account.service.AuthClientService;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.util.ConvertUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 授权客户端(AuthClient) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:01
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthClientBiz implements CrudBiz<AuthClientRequest, AuthClient, java.lang.Long, AuthClientSearchDto, AuthClientSearchDto, AuthClientService>, AuthClientRest {

    private final TenantContextHolder tenantContextHolder;
    private final RedisConnectionFactory redisConnectionFactory;
    private final AuthClientScopesCacheProperties authClientScopesCacheProperties;
    private final AuthClientService authClientService;

    @Override
    public AuthClient resolver(TokenInfo token, AuthClientRequest request) {
        AuthClient authClient = new AuthClient();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, authClient);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            authClient.setTenantId(token.getTenantId());
        }
        else {
            authClient.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        if (isNull(authClient.getScopes())) {
            authClient.setScopes("");
        }
        if (isNull(authClient.getRoleIds())) {
            authClient.setScopes("");
        }
        return authClient;
    }

    @Override
    public AuthClientService getCrudService() {
        return authClientService;
    }

    @Override
    public AuthClientSearchDto getSearchDto() {
        return new AuthClientSearchDto();
    }

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Set<String> getScopesByClientIdAndClientSecret(String clientId, String clientSecret) {
        String tenantId = tenantContextHolder.getTenantId();
        LambdaQueryWrapper<AuthClient> queryWrapper = Wrappers.lambdaQuery(AuthClient.class);
        queryWrapper.select(AuthClient::getScopes)
                    .eq(AuthClient::getTenantId, tenantId)
                    .eq(AuthClient::getClientId, clientId)
                    .eq(AuthClient::getClientSecret, clientSecret)
                    .eq(AuthClient::getDeleted, FALSE);
        AuthClient authClient = authClientService.findOneBySpec(queryWrapper);
        if (isNull(authClient)) {
            return Collections.emptySet();
        }
        String scopesString = authClient.getScopes();
        if (isNull(scopesString)) {
            return Collections.emptySet();
        }
        return StringUtils.commaDelimitedListToSet(scopesString);
    }

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes, Map(tenantId, Map(clientId, AuthClientVo))
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Map<Integer, Map<String, AuthClientVo>> getAllScopes() {
        LambdaQueryWrapper<AuthClient> queryWrapper = Wrappers.lambdaQuery(AuthClient.class);
        queryWrapper.select(AuthClient::getTenantId, AuthClient::getClientId, AuthClient::getClientSecret,
                            AuthClient::getScopes, AuthClient::getRoleIds)
                    .eq(AuthClient::getDeleted, FALSE);
        List<AuthClient> authClientList = authClientService.findAllBySpec(queryWrapper);
        List<AuthClientVo> authClientVoList = authClientList.stream()
                                                            .map(authClient -> {
                                                                AuthClientVo authClientVo = new AuthClientVo();
                                                                BeanUtils.copyProperties(authClient, authClientVo);
                                                                return authClientVo;
                                                            })
                                                            .collect(Collectors.toList());
        int size = authClientVoList.size();
        final Map<Integer, Map<String, AuthClientVo>> map = new HashMap<>(size);
        if (size == 0) {
            return map;
        }
        authClientVoList.forEach(vo -> map.compute(vo.getTenantId(), (tenantId, valueMap) -> {
            if (isNull(valueMap)) {
                valueMap = new HashMap<>(1);
            }
            valueMap.put(vo.getClientId(), vo);
            return valueMap;
        }));

        return map;
    }

    /**
     * 根据 id 更新实体
     *
     * @param request 实体 request
     * @return 返回更新后的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateById(AuthClientRequest request) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        AuthClient entity = resolver(tokenInfo, request);
        boolean updateById = authClientService.updateById(entity);
        setRedisSyncFlag();
        return updateById;
    }


    /**
     * 保存单个实体
     *
     * @param request 实体 request
     * @return 返回保存的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AuthClientSearchDto save(AuthClientRequest request) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        AuthClient authClient = resolver(tokenInfo, request);
        final boolean save = authClientService.save(authClient);
        setRedisSyncFlag();
        return convertReturnDto(authClient);
    }

    /**
     * 保存多个实体
     *
     * @param authClientRequestList 实体 request List
     * @return 返回保存的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<AuthClientSearchDto> saveAll(List<AuthClientRequest> authClientRequestList) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        List<AuthClient> authClientList =
                authClientRequestList.stream()
                                     .map(request -> resolver(tokenInfo, request))
                                     .collect(Collectors.toList());
        final boolean saveBatch = authClientService.saveList(authClientList);
        setRedisSyncFlag();
        return authClientList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /***
     * 逻辑删除实体
     *
     * @param id 主键id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteById(Long id) {
        AuthClient authClient = new AuthClient();
        authClient.setId(id);
        authClient.setDeleted(FALSE);
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.lambdaUpdate(authClient).set(AuthClient::getDeleted, 1);
        // 逻辑删除
        final boolean remove = authClientService.updateBySpec(updateWrapper);
        setRedisSyncFlag();
        return remove;
    }

    /***
     * 逻辑删除实体
     *
     * @param request 需要删除的实体
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteById(AuthClientRequest request) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        AuthClient authClient = resolver(tokenInfo, request);
        request.setDeleted(FALSE);
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.lambdaUpdate(authClient).set(AuthClient::getDeleted,
                                                                                              Boolean.TRUE);
        // 逻辑删除
        final boolean remove = authClientService.updateBySpec(updateWrapper);
        setRedisSyncFlag();
        return remove;
    }

    /***
     * 根据主键集合逻辑删除实体
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteAllById(List<Long> ids) {
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.<AuthClient>lambdaUpdate()
                                                                .eq(AuthClient::getDeleted, 0)
                                                                .in(AuthClient::getId, ids)
                                                                .set(AuthClient::getDeleted, 1);

        // 逻辑删除
        final boolean remove = authClientService.updateBySpec(updateWrapper);
        setRedisSyncFlag();
        return remove;
    }

    /**
     * 角色权限检查: 根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
     * @param scopeId   {@link AuthClient#getId()}
     * @param roleId    角色 ID
     * @return  返回 true 表示包含此角色(roleId), 否则返回 false
     */
    @Transactional(readOnly = true)
    public boolean hasRoleId(@NonNull Long scopeId, @NonNull Long roleId) {
        AuthClient authClient = authClientService.findById(scopeId);
        String roleIds = authClient.getRoleIds();
        if (StringUtils.hasText(roleIds)) {
            Set<String> roleSet = ConvertUtil.string2Set(roleIds, RoleConstants.AUTHORITY_SEPARATOR);
            return roleSet.contains(roleId.toString());
        }
        return false;
    }

    private RedisConnection getConnection() {
        return redisConnectionFactory.getConnection();
    }

    /**
     * 设置 redis 同步标志.<br> TODO 待优化改成 MQ
     * {@code ArchJwkEndpointPermissionServiceImpl#run()} 定时同步任务将执行同步到本地缓存的操作.
     */
    private void setRedisSyncFlag() {
        try (final RedisConnection connection = getConnection()) {
            AuthClientSyncUtils.setScopesUpdateSyncFlag(this.authClientScopesCacheProperties, connection);
        }
    }

}
