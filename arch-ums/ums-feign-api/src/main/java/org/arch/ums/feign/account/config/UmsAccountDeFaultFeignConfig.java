package org.arch.ums.feign.account.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.arch.framework.feign.config.FeignGlobalConfig;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

/**
 * 账号模块默认的 feign 配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:46
 */
@Configuration
public class UmsAccountDeFaultFeignConfig extends FeignGlobalConfig {

    public UmsAccountDeFaultFeignConfig(TenantContextHolder tenantContextHolder,
                                        AppProperties appProperties) {
        super(tenantContextHolder, appProperties.getTenantHeaderName());
    }

    @Bean
    @Primary
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }


}
