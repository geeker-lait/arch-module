package org.arch.framework.config;

public interface ComponentConfig {
    MiddlewareConfig getConfig(String var1, String var2);

    String getAppId();

    String getSecret();

    String getApplicationName();

    String getRedisKeyPrefix();
}
