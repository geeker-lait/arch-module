package org.arch.ums.config;

import org.arch.framework.beans.exception.handler.RestExceptionHandler;
import org.arch.framework.beans.exception.i18n.UnifiedMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Configuration
public class AdviceConfiguration {

    @Bean
    public UnifiedMessageSource unifiedMessageSource() {
        return new UnifiedMessageSource();
    }

    @Bean
    public ResponseBodyAdvice<Object> restResponseBodyAdvice() {
        return new RestExceptionHandler();
    }
}
