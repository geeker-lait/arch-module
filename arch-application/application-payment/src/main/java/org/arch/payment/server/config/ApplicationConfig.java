package org.arch.payment.server.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.arch.framework.client.jdbc.JdbcClient;
import org.arch.framework.id.IdService;
import org.arch.framework.id.impl.RedisIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.Locale;

/**
 * 程序相关配置
 */
@Configuration
public class ApplicationConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public IdService idService() {
        return new RedisIdService(redisConnectionFactory);
    }


    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    @ConfigurationProperties(prefix = "serviceUrl")
//    public ServiceUrl serviceUrl() {
//        return new ServiceUrl();
//    }

//    @Bean
//    public ExceptionFactory exceptionFactory() {
//        return new ExceptionFactory(messageSource);
//    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Bean
    public JdbcClient jdbcClient() {
        return new JdbcClient(jdbcTemplate);
    }

//    @Bean
//    public RestClient restClient() {
//        return new RestClient(createRestTemplate(), serviceUrl());
//    }

}
