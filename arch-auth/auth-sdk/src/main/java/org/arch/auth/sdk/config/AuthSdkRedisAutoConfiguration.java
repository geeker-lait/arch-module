package org.arch.auth.sdk.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.arch.auth.jwt.config.JwtRedisSerializerAutoConfiguration;
import org.arch.auth.sdk.redis.properties.AuthSdkRedisProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import top.dcenter.ums.security.jwt.config.JwtAutoConfiguration;

import java.time.Duration;

/**
 * auth-sdk redis 自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.17 23:07
 */
@Configuration
@AutoConfigureAfter({AuthSdkAutoConfiguration.class})
@AutoConfigureBefore({JwtRedisSerializerAutoConfiguration.class, JwtAutoConfiguration.class})
public class AuthSdkRedisAutoConfiguration {

    private final AuthSdkRedisProperties authSdkRedisProperties;

    public AuthSdkRedisAutoConfiguration(AuthSdkRedisProperties authSdkRedisProperties) {
        this.authSdkRedisProperties = authSdkRedisProperties;
    }

    @Bean("jwtRedisConnectionFactory")
    public LettuceConnectionFactory jwtRedisConnectionFactory() {
        AuthSdkRedisProperties.Lettuce lettuce = authSdkRedisProperties.getLettuce();
        AuthSdkRedisProperties.Pool pool = lettuce.getPool();
        return createLettuceConnectionFactory(authSdkRedisProperties.getDatabase(),
                                              authSdkRedisProperties.getHost(),
                                              authSdkRedisProperties.getPort(),
                                              authSdkRedisProperties.getPassword(),
                                              pool.getMaxIdle(),
                                              pool.getMinIdle(),
                                              pool.getMaxActive(),
                                              pool.getMaxWait().getSeconds(),
                                              authSdkRedisProperties.getTimeout().toMillis(),
                                              lettuce.getShutdownTimeout());
    }

    /**
     * 自定义 LettuceConnectionFactory
     */
    private LettuceConnectionFactory createLettuceConnectionFactory(
            int dbIndex, String hostName, int port, String password,
            int maxIdle, int minIdle, int maxActive,
            Long maxWait, Long timeOut, Duration shutdownTimeOut){

        //redis配置
        RedisConfiguration redisConfiguration = new
                RedisStandaloneConfiguration(hostName, port);
        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(dbIndex);
        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(password);

        //连接池配置
        //noinspection rawtypes
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        //redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder =  LettucePoolingClientConfiguration.builder().
                commandTimeout(Duration.ofMillis(timeOut));

        builder.shutdownTimeout(shutdownTimeOut);
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);

        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

}
