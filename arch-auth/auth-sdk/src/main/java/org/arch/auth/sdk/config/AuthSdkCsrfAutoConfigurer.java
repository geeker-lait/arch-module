package org.arch.auth.sdk.config;

import lombok.RequiredArgsConstructor;
import org.arch.auth.sdk.properties.CsrfProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import top.dcenter.ums.security.common.api.config.HttpSecurityAware;
import top.dcenter.ums.security.common.bean.UriHttpMethodTuple;
import top.dcenter.ums.security.common.config.SecurityCoreAutoConfigurer;

import java.util.Map;
import java.util.Set;

/**
 * auth sdk csrf 自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.21 11:49
 */
@Configuration
@AutoConfigureBefore({SecurityCoreAutoConfigurer.class})
@RequiredArgsConstructor
public class AuthSdkCsrfAutoConfigurer implements HttpSecurityAware {

    private final CsrfProperties csrfProperties;

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

        if (csrfProperties.getEnable())
        {
            CsrfConfigurer<HttpSecurity> csrf = http.csrf();

            Set<String> ignoringAntMatcherUrls = csrfProperties.getIgnoringAntMatcherUrls();
            String[] urls = new String[ignoringAntMatcherUrls.size()];
            ignoringAntMatcherUrls.toArray(urls);

            csrf.ignoringAntMatchers(urls);

        } else
        {
            http.csrf().disable();
        }

    }

    @Override
    public Map<String, Map<UriHttpMethodTuple, Set<String>>> getAuthorizeRequestMap() {
        return null;
    }
}
