package org.arch.auth.sso.tenant.config;

import lombok.RequiredArgsConstructor;
import org.arch.auth.sso.tenant.context.filter.TenantContextFilter;
import org.arch.auth.sso.tenant.context.handler.ArchTenantContextHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import top.dcenter.ums.security.common.api.config.HttpSecurityAware;
import top.dcenter.ums.security.common.bean.UriHttpMethodTuple;
import top.dcenter.ums.security.core.mdc.config.MdcLogAutoConfigurerAware;

import java.util.Map;
import java.util.Set;

/**
 * 多租户上下文过滤器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.11 21:59
 */
//@Configuration
@AutoConfigureAfter({MdcLogAutoConfigurerAware.class})
@RequiredArgsConstructor
public class TenantContextConfigurationAware implements HttpSecurityAware {

    private final ArchTenantContextHandler archTenantContextHandler;

    @Override
    public void configure(WebSecurity web) {
        // nothing
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        // nothing
    }

    @Override
    public void preConfigure(HttpSecurity http) {
        // nothing
    }

    @Override
    public void postConfigure(HttpSecurity http) {
        http.addFilterAfter(new TenantContextFilter(archTenantContextHandler), WebAsyncManagerIntegrationFilter.class);
    }

    @Override
    public Map<String, Map<UriHttpMethodTuple, Set<String>>> getAuthorizeRequestMap() {
        return null;
    }
}
