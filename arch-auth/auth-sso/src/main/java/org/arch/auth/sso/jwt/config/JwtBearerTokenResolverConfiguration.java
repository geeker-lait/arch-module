/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arch.auth.sso.jwt.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import top.dcenter.ums.security.core.auth.properties.ClientProperties;
import top.dcenter.ums.security.core.auth.properties.SmsCodeLoginAuthenticationProperties;
import top.dcenter.ums.security.core.oauth.properties.Auth2Properties;
import top.dcenter.ums.security.jwt.config.JwtServiceAutoConfiguration;
import top.dcenter.ums.security.jwt.resolver.UmsBearerTokenResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * 针对 {@link UmsBearerTokenResolver} 的 ignoreUrls 的自动配置
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.8 12:56
 */
@Configuration
@AutoConfigureAfter({JwtServiceAutoConfiguration.class})
public class JwtBearerTokenResolverConfiguration {

    public JwtBearerTokenResolverConfiguration(ClientProperties clientProperties,
                                               Auth2Properties auth2Properties,
                                               BearerTokenResolver bearerTokenResolver,
                                               SmsCodeLoginAuthenticationProperties smsCodeLoginAuthenticationProperties) {
        Set<String> ignoreUrls = new HashSet<>();
        ignoreUrls.add(clientProperties.getLoginPage());
        ignoreUrls.add(clientProperties.getLoginProcessingUrl());
        if (clientProperties.getOpenAuthenticationRedirect()) {
            ignoreUrls.add(clientProperties.getLoginUnAuthenticationRoutingUrl());
        }
        if (smsCodeLoginAuthenticationProperties.getSmsCodeLoginIsOpen()) {
            ignoreUrls.add(smsCodeLoginAuthenticationProperties.getLoginProcessingUrlMobile());
        }
        if (auth2Properties.getEnabled()) {
            ignoreUrls.add(auth2Properties.getAuthLoginUrlPrefix() + "/*");
            ignoreUrls.add(auth2Properties.getRedirectUrlPrefix() + "/*");
        }
        if (bearerTokenResolver instanceof UmsBearerTokenResolver) {
            UmsBearerTokenResolver tokenResolver = ((UmsBearerTokenResolver) bearerTokenResolver);
            tokenResolver.addIgnoreUrls(ignoreUrls);
        }
    }

}
