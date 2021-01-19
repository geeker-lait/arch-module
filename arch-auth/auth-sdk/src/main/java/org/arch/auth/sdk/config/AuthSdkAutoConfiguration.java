package org.arch.auth.sdk.config;

import org.arch.auth.jwt.config.ArchJwtServiceAutoConfiguration;
import org.arch.auth.rbac.config.ArchRbacAutoConfiguration;
import org.arch.auth.sdk.jwt.properties.ArchJwtProperties;
import org.arch.auth.sdk.jwt.properties.ArchOauth2ResourceServerJwtProperties;
import org.arch.auth.sdk.rbac.properties.ArchRbacProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.core.premission.config.RoleHierarchyAutoConfiguration;
import top.dcenter.ums.security.core.premission.config.UriAuthorizeAutoConfigurerAware;
import top.dcenter.ums.security.core.premission.properties.PermissionProperties;
import top.dcenter.ums.security.jwt.config.JwtPropertiesAutoConfiguration;
import top.dcenter.ums.security.jwt.properties.JwtProperties;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * 从 auth-rbac 与 auth-jwt jar 包读取 yml 配置, 再设置到 jwt 与 rbac 对应的 properties 对象中
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.17 21:53
 */
@Configuration
@AutoConfigureAfter({JwtPropertiesAutoConfiguration.class})
@AutoConfigureBefore({ArchJwtServiceAutoConfiguration.class, ArchRbacAutoConfiguration.class,
                      RoleHierarchyAutoConfiguration.class, UriAuthorizeAutoConfigurerAware.class})
public class AuthSdkAutoConfiguration {

    public AuthSdkAutoConfiguration(ArchRbacProperties archRbacProperties,
                                    ArchJwtProperties archJwtProperties,
                                    ArchOauth2ResourceServerJwtProperties archOauth2ResourceServerJwtProperties,
                                    OAuth2ResourceServerProperties oAuth2ResourceServerProperties,
                                    JwtProperties jwtProperties,
                                    PermissionProperties permissionProperties) {
        // 从 auth-rbac 与 auth-jwt jar 包读取 yml 配置, 再设置到 jwt 与 rbac 对应的 properties 对象中
        copyProperties(archRbacProperties, permissionProperties);
        copyProperties(archJwtProperties, jwtProperties);
        copyProperties(archJwtProperties.getBearer(), jwtProperties.getBearer());
        copyProperties(archJwtProperties.getBlacklist(), jwtProperties.getBlacklist());
        copyProperties(archOauth2ResourceServerJwtProperties, oAuth2ResourceServerProperties.getJwt());
    }

}
