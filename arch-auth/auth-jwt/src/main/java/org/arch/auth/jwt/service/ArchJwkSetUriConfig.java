package org.arch.auth.jwt.service;

import com.nimbusds.jose.jwk.source.DefaultJWKSetCache;
import lombok.RequiredArgsConstructor;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.jwt.api.endpoind.service.JwkSetUriConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.arch.framework.ums.consts.ClientConstants.CLIENT_CODE_HEADER_NAME;
import static org.arch.framework.ums.consts.ClientConstants.CLIENT_ID_HEADER_NAME;

/**
 * 用于从 jwk set uri 获取 JWk 时传递 header 的参数,
 * 获取 jwk set 后缓存的时间, 访问 jwk set uri 的频率 的参数接口,
 * 通过 UmsNimbusJwtDecoder.RestOperationsResourceRetriever 传递 header 参数.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.19 16:31
 */
@RequiredArgsConstructor
public class ArchJwkSetUriConfig implements JwkSetUriConfig {

    private final AppProperties appProperties;

    @NonNull
    @Override
    public Map<String, Object> headers() {
        Map<String, Object> headers = new HashMap<>(2);
        headers.put(CLIENT_ID_HEADER_NAME, appProperties.getClientId());
        headers.put(CLIENT_CODE_HEADER_NAME, appProperties.getClientSecret());
        return headers;
    }

    @Override
    public long lifespan() {
        return DefaultJWKSetCache.DEFAULT_LIFESPAN_MINUTES;
    }

    @Override
    public long refreshTime() {
        return DefaultJWKSetCache.DEFAULT_REFRESH_TIME_MINUTES;
    }

    @NonNull
    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }
}
