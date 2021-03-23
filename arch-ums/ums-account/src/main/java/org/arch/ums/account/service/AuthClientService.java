package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.ums.properties.AuthClientScopesCacheProperties;
import org.arch.ums.account.dao.AuthClientDao;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * 授权客户端(AuthClient) 表服务层<br>
 * 注意: 增加/更新/删除 操作后, 需要调用 {@link #setRedisSyncFlag()} 方法.
 * @author YongWu zheng
 * @date 2021-01-29 20:48:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthClientService extends CrudService<AuthClient, java.lang.Long> {

    private final AuthClientDao authClientDao;

    /**
     * redisConnectionFactory.
     * 注意: 增加/更新/删除 操作后, 需要调用 {@link #setRedisSyncFlag()} 方法.
     */
    private final RedisConnectionFactory redisConnectionFactory;
    private final AuthClientScopesCacheProperties authClientScopesCacheProperties;

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Set<String> getScopesByClientIdAndClientSecret(String clientId, String clientSecret) {
        String scopesString = authClientDao.getScopesByClientIdAndClientSecret(clientId, clientSecret);
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
    @NonNull
    public Map<Integer, Map<String, AuthClientVo>> getAllScopes() {
        List<AuthClientVo> list = authClientDao.getAllScopes();
        if (isNull(list)) {
            return Collections.emptyMap();
        }
        final Map<Integer, Map<String, AuthClientVo>> map = new HashMap<>(list.size());
        list.forEach(vo -> map.compute(vo.getTenantId(), (tenantId, valueMap) -> {
            if (isNull(valueMap)) {
                valueMap = new HashMap<>();
            }
            valueMap.put(vo.getClientId(), vo);
            return valueMap;
        }));
        return map;
    }


    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean save(AuthClient t) {
        final boolean save = authClientDao.save(t);
        setRedisSyncFlag();
        return save;
    }

    /**
     * 保存多个实体
     *
     * @param authClientList 实体
     * @return 返回保存的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveList(List<AuthClient> authClientList) {
        final boolean saveBatch = authClientDao.saveBatch(authClientList);
        setRedisSyncFlag();
        return saveBatch;
    }

    /***
     * 逻辑删除实体
     *
     * @param id 主键id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        AuthClient authClient = new AuthClient();
        authClient.setId(id);
        authClient.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.lambdaUpdate(authClient).set(AuthClient::getDeleted, 1);
        // 逻辑删除
        final boolean remove = authClientDao.update(updateWrapper);
        setRedisSyncFlag();
        return remove;
    }

    /***
     * 逻辑删除实体
     *
     * @param t 需要删除的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(AuthClient t) {
        t.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.lambdaUpdate(t).set(AuthClient::getDeleted, 1);
        // 逻辑删除
        final boolean remove = authClientDao.update(updateWrapper);
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
    public boolean deleteAllById(List<Long> ids) {
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.<AuthClient>lambdaUpdate()
                                                                .eq(AuthClient::getDeleted, 0)
                                                                .in(AuthClient::getId, ids)
                                                                .set(AuthClient::getDeleted, 1);

        // 逻辑删除
        final boolean remove = authClientDao.update(updateWrapper);
        setRedisSyncFlag();
        return remove;
    }


    private RedisConnection getConnection() {
        return redisConnectionFactory.getConnection();
    }

    /**
     * 设置 redis 同步标志.<br>
     * {@code ArchJwkEndpointPermissionServiceImpl#run()} 定时同步任务将执行同步到本地缓存的操作.
     */
    private void setRedisSyncFlag() {
        try (final RedisConnection connection = getConnection()) {
            connection.del(authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashKey().getBytes(StandardCharsets.UTF_8));
            connection.hSet(authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashKey().getBytes(StandardCharsets.UTF_8),
                            authClientScopesCacheProperties.getScopesCacheUpdatedRedisHashField().getBytes(StandardCharsets.UTF_8),
                            "1".getBytes(StandardCharsets.UTF_8));
        }
    }

}