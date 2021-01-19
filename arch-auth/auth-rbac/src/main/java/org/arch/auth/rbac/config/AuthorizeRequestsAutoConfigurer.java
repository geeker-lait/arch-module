package org.arch.auth.rbac.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import top.dcenter.ums.security.common.api.config.HttpSecurityAware;
import top.dcenter.ums.security.common.bean.UriHttpMethodTuple;
import top.dcenter.ums.security.common.config.SecurityCoreAutoConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 因为 authorizeRequests 配置时候要 authorizeRequests().anyRequest().authenticate 放到最后，
 * 所以这里临时把 权限与 uri 放入 map 给主配置器处理. 最终在{@link SecurityCoreAutoConfigurer} 中 configure(HttpSecurity) 方法中配置.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.19 23:55
 */
@Configuration
@AutoConfigureBefore({SecurityCoreAutoConfigurer.class})
public class AuthorizeRequestsAutoConfigurer implements HttpSecurityAware {

    public static final String JWS_SET_URI = "/.well-known/jwks.json";

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
        /*
          这里有 12 种权限类型的配置, 具体配置方式看方法注释说明中的示例.
          PERMIT_ALL / DENY_ALL / ANONYMOUS / AUTHENTICATED / FULLY_AUTHENTICATED / REMEMBER_ME /
          ACCESS / HAS_ROLE / HAS_ANY_ROLE / HAS_AUTHORITY / HAS_ANY_AUTHORITY / HAS_IP_ADDRESS /
         */
        Map<String, Map<UriHttpMethodTuple, Set<String>>> resultMap = new HashMap<>(12);

        // PERMIT_ALL = "permitAll";
        Map<UriHttpMethodTuple, Set<String>> permitAllMap = new HashMap<>(16);
        permitAllMap.put(UriHttpMethodTuple.tuple(HttpMethod.GET, JWS_SET_URI), null);
        resultMap.put(HttpSecurityAware.PERMIT_ALL, permitAllMap);



        return resultMap;
    }
}
