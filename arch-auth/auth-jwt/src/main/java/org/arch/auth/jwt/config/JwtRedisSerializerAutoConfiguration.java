package org.arch.auth.jwt.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.arch.auth.jwt.jackson2.model.AuthSdkJackson2Module;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.jackson2.WebJackson2Module;
import org.springframework.security.web.jackson2.WebServletJackson2Module;
import org.springframework.security.web.server.jackson2.WebServerJackson2Module;
import top.dcenter.ums.security.jwt.JwtContext;
import top.dcenter.ums.security.jwt.config.JwtPropertiesAutoConfiguration;
import top.dcenter.ums.security.jwt.config.RedisSerializerAutoConfiguration;

/**
 * redis 序列化器自动配置, 用于 {@link JwtContext} 存储
 * @author YongWu zheng
 * @since 2021.1.1 15:42
 */
@Configuration
@AutoConfigureAfter({JwtPropertiesAutoConfiguration.class})
@AutoConfigureBefore({RedisSerializerAutoConfiguration.class})
public class JwtRedisSerializerAutoConfiguration {

    /**
     * 配置 Jackson2JsonRedisSerializer 序列化器, 覆盖 ums-jwt 模块的同名 bean 序列化器
     */
    @Bean("jwtTokenRedisSerializer")
    public RedisSerializer<TokenInfo> jwtRedisSerializer(){
        Jackson2JsonRedisSerializer<TokenInfo> jackson2JsonRedisSerializer
                 = new Jackson2JsonRedisSerializer<>(TokenInfo.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                                     ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModules(new CoreJackson2Module(), new WebJackson2Module(), new WebServletJackson2Module(),
                               new JavaTimeModule(), new WebServerJackson2Module(), new AuthSdkJackson2Module());
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        return jackson2JsonRedisSerializer;
    }

}