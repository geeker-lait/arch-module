package org.arch.framework.utils;

import org.arch.framework.ums.properties.AuthClientScopesCacheProperties;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.lang.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * {@code AuthClient} 同步工具集
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.23 15:48
 */
public class AuthClientSyncUtils {

    /**
     * AuthClient scopes 更新后, 设置 redis 同步标志, 让缓存 scopes 的其他微服务进行更新操作<br>
     *
     * @param properties    {@link AuthClientScopesCacheProperties}
     * @param connection    {@link RedisConnection} 此方法未调用 {@link RedisConnection#close()} 方法.
     */
    public static void setScopesUpdateSyncFlag(@NonNull AuthClientScopesCacheProperties properties,
                                        @NonNull RedisConnection connection) {
        connection.del(properties.getScopesCacheUpdatedRedisHashKey().getBytes(StandardCharsets.UTF_8));
        // "AUTH:CLIENT:SCOPES:UPDATED" "ALL" = 1
        connection.hSet(properties.getScopesCacheUpdatedRedisHashKey().getBytes(StandardCharsets.UTF_8),
                  properties.getScopesCacheUpdatedRedisHashField().getBytes(StandardCharsets.UTF_8),
                  "1".getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 本地 AuthClient scopes 更新后, 设置本应用 redis 同步标志, 以便下次检查时忽略本应用的 scopes 更新操作<br>
     *
     * @param properties      {@link AuthClientScopesCacheProperties}
     * @param connection      {@link RedisConnection} 此方法未调用 {@link RedisConnection#close()} 方法.
     * @param scopeUpdateUuid 代表微服务的唯一标识.
     */
    public static void setScopesLocalUpdateSyncFlag(@NonNull AuthClientScopesCacheProperties properties,
                                                    @NonNull RedisConnection connection,
                                                    @NonNull String scopeUpdateUuid) {

        // 标记本地缓存已经更新.
        connection.hSet(properties.getScopesCacheUpdatedRedisHashKey()
                                  .getBytes(StandardCharsets.UTF_8),
                        scopeUpdateUuid.getBytes(StandardCharsets.UTF_8),
                        "1".getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 检查本微服务是否需要更新 scopes 缓存
     *
     * @param properties      {@link AuthClientScopesCacheProperties}
     * @param connection      {@link RedisConnection} 此方法未调用 {@link RedisConnection#close()} 方法.
     * @param scopeUpdateUuid 代表微服务的唯一标识.
     * @return 返回 true 表示需要更新, 否则不需要更新.
     */
    public static boolean checkScopesUpdate(@NonNull AuthClientScopesCacheProperties properties,
                                            @NonNull RedisConnection connection,
                                            @NonNull String scopeUpdateUuid) {

        List<byte[]> list = connection.hMGet(properties.getScopesCacheUpdatedRedisHashKey()
                                                       .getBytes(StandardCharsets.UTF_8),
                                             properties.getScopesCacheUpdatedRedisHashField()
                                                       .getBytes(StandardCharsets.UTF_8),
                                             scopeUpdateUuid.getBytes(StandardCharsets.UTF_8));
        if (isNull(list) || isNull(list.get(0)) || nonNull(list.get(1))) {
            // "AUTH:CLIENT:SCOPES:UPDATED" "ALL" 没有值, 或 scopeUpdateUuid 有值, 不需要更新本地缓存
            return false;
        }

        // hGet "AUTH:CLIENT:SCOPES:UPDATED" scopeUpdateUuid 没有值, 需要更新本地缓存
        return true;
    }
}
