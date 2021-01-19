package org.arch.auth.sdk.config;

import lombok.RequiredArgsConstructor;
import org.arch.framework.ums.config.ArchBaseAutoConfiguration;
import org.arch.framework.ums.tenant.context.filter.TenantContextFilter;
import org.arch.framework.ums.tenant.context.handler.ArchTenantContextHolder;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import top.dcenter.ums.security.common.api.config.HttpSecurityAware;
import top.dcenter.ums.security.common.bean.UriHttpMethodTuple;
import top.dcenter.ums.security.common.config.SecurityCoreAutoConfigurer;

import java.util.Map;
import java.util.Set;

/**
 * 租户过滤器配置.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.19 20:56
 */
@Configuration
@AutoConfigureAfter({ArchBaseAutoConfiguration.class})
@AutoConfigureBefore({SecurityCoreAutoConfigurer.class})
@RequiredArgsConstructor
public class TenantContextFilterAutoConfigurer implements HttpSecurityAware {

    private final ArchTenantContextHolder archTenantContextHolder;

    @Override
    public void configure(WebSecurity web) {
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    @Override
    public void preConfigure(HttpSecurity http) throws Exception {
    }

    @Override
    public void postConfigure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new TenantContextFilter(archTenantContextHolder), WebAsyncManagerIntegrationFilter.class);
    }

    @Override
    public Map<String, Map<UriHttpMethodTuple, Set<String>>> getAuthorizeRequestMap() {
        return null;
    }
}
