package org.arch.ums.feign.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.arch.framework.feign.config.FeignGlobalConfig;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.feign.encoder.ArchSpringEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

/**
 * 账号模块默认的 feign 配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:46
 */
@Configuration
public class UmsAccountDeFaultFeignConfig extends FeignGlobalConfig {

    public UmsAccountDeFaultFeignConfig(TenantContextHolder tenantContextHolder,
                                        AppProperties appProperties, JwtProperties jwtProperties) {
        super(tenantContextHolder, appProperties.getTenantHeaderName(), jwtProperties.getClockSkew());
    }

    @Bean
    public Encoder springEncode(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new ArchSpringEncoder(new SpringFormEncoder(), messageConverters);
    }

}
