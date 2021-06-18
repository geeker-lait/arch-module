package org.arch.framework.encrypt.config;

import org.arch.framework.encrypt.DelegatingEncryptHandler;
import org.arch.framework.encrypt.EncryptService;
import org.arch.framework.encrypt.FF3Cipher;
import org.arch.framework.encrypt.impl.BCryptEncryptServiceImpl;
import org.arch.framework.encrypt.impl.Ff3EncryptServiceImpl;
import org.arch.framework.encrypt.impl.TextEncryptorEncryptServiceImpl;
import org.arch.framework.encrypt.impl.TotpEncryptServiceImpl;
import org.arch.framework.encrypt.intercept.EncryptParameterInterceptor;
import org.arch.framework.encrypt.intercept.EncryptResultInterceptor;
import org.arch.framework.ums.config.AppPropertiesAutoConfiguration;
import org.arch.framework.ums.properties.EncryptProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 数据库表字段加解密的自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 16:40
 */
@Configuration
@AutoConfigureAfter({AppPropertiesAutoConfiguration.class})
public class ColumnEncryptAutoConfiguration {

    @Bean
    public BCryptEncryptServiceImpl bCryptEncryptService() {
        return new BCryptEncryptServiceImpl();
    }

    @Bean
    public Ff3EncryptServiceImpl ff3EncryptServiceImpl(EncryptProperties encryptProperties) {
        EncryptProperties.FF3Properties ff3Properties = encryptProperties.getFpe();
        FF3Cipher ff3Cipher = new FF3Cipher(ff3Properties.getPassword(), ff3Properties.getTweak() ,
                                            ff3Properties.getRadix());
        return new Ff3EncryptServiceImpl(ff3Cipher);
    }

    @Bean
    public TextEncryptorEncryptServiceImpl textEncryptorEncryptService(EncryptProperties encryptProperties) {
        return new TextEncryptorEncryptServiceImpl(encryptProperties);
    }

    @Bean
    public TotpEncryptServiceImpl totpEncryptService(EncryptProperties encryptProperties) {
        return new TotpEncryptServiceImpl(encryptProperties);
    }

    @Bean
    public DelegatingEncryptHandler delegatingEncryptHandler(Map<String, EncryptService> encryptServiceMap) {
        return new DelegatingEncryptHandler(encryptServiceMap);
    }

    @Bean
    public EncryptParameterInterceptor encryptParameterInterceptor(DelegatingEncryptHandler delegatingEncryptHandler) {
        return new EncryptParameterInterceptor(delegatingEncryptHandler);
    }

    @Bean
    public EncryptResultInterceptor encryptResultInterceptor(DelegatingEncryptHandler delegatingEncryptHandler) {
        return new EncryptResultInterceptor(delegatingEncryptHandler);
    }

}
