package org.arch.framework.id.config;

import org.arch.framework.id.IdService;
import org.arch.framework.id.impl.RedisIdService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 分布式 IdService 的自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 16:40
 */
@Configuration
public class ArchIdServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(type = "org.arch.framework.id.IdService")
    public IdService idService(RedisConnectionFactory redisConnectionFactory) {
        return new RedisIdService(redisConnectionFactory);
    }
}
