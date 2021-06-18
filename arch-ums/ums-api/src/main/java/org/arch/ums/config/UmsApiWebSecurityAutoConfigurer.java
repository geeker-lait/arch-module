package org.arch.ums.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.common.config.SecurityCoreAutoConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 核心 HttpSecurity 安全相关配置
 *
 * @author zhailiang
 * @version V1.0  Created by 2020/5/3 13:14
 * @author YongWu zheng
 */
@Configuration
public class UmsApiWebSecurityAutoConfigurer {

    @Bean
    public SecurityCoreAutoConfigurer securityCoreAutoConfigurer() {
        return new SecurityCoreAutoConfigurer();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // localDateTime 按照 "yyyy-MM-dd HH:mm:ss" 的格式进行序列化、反序列化
        javaTimeModule.addDeserializer(LocalDateTime.class,
                                       new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDateTime.class,
                                     new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }
}