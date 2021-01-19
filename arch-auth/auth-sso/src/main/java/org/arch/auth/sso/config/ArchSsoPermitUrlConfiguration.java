package org.arch.auth.sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import top.dcenter.ums.security.common.api.config.HttpSecurityAware;
import top.dcenter.ums.security.common.bean.UriHttpMethodTuple;
import top.dcenter.ums.security.jwt.endpoint.JwkEndpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * sso 忽略 url 配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.19 12:05
 */
@Configuration
public class ArchSsoPermitUrlConfiguration implements HttpSecurityAware {
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
    }

    @Override
    public Map<String, Map<UriHttpMethodTuple, Set<String>>> getAuthorizeRequestMap() {

        Map<String, Map<UriHttpMethodTuple, Set<String>>> resultMap = new HashMap<>(1);
        // PERMIT_ALL = "permitAll";
        Map<UriHttpMethodTuple, Set<String>> permitAllMap = new HashMap<>(1);
        permitAllMap.put(UriHttpMethodTuple.tuple(HttpMethod.GET, JwkEndpoint.JWS_SET_URI), null);
        resultMap.put(HttpSecurityAware.PERMIT_ALL, permitAllMap);

        return resultMap;
    }
}
