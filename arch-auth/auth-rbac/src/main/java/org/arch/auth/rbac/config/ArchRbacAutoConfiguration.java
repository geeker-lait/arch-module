package org.arch.auth.rbac.config;

import org.arch.auth.rbac.feign.RoleGroupFeignService;
import org.arch.auth.rbac.feign.RoleMenuFeignService;
import org.arch.auth.rbac.feign.RolePermissionFeignService;
import org.arch.auth.rbac.feign.RoleResourceFeignService;
import org.arch.auth.rbac.service.ArchAuthoritiesServiceImpl;
import org.arch.auth.rbac.service.ArchRbacUriAuthorizeServiceImpl;
import org.arch.auth.rbac.service.AuthoritiesService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.core.api.premission.service.AbstractUriAuthorizeService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.premission.config.PermissionAutoConfiguration;

/**
 * RBAC 权限服务自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.18 17:38
 */
@Configuration
@AutoConfigureAfter({ArchRbacFeignAutoConfiguration.class})
@AutoConfigureBefore({PermissionAutoConfiguration.class})
public class ArchRbacAutoConfiguration {

    @Bean
    public AbstractUriAuthorizeService abstractUriAuthorizeService(TenantContextHolder tenantContextHolder,
                                                                   AuthoritiesService authoritiesService) {
        return new ArchRbacUriAuthorizeServiceImpl(tenantContextHolder,
                                                   authoritiesService);
    }

    @Bean
    @ConditionalOnMissingBean(type = "org.arch.auth.rbac.service.AuthoritiesService")
    public AuthoritiesService authoritiesService(RoleMenuFeignService roleMenuFeignService,
                                                 RoleGroupFeignService roleGroupFeignService,
                                                 RoleResourceFeignService roleResourceFeignService,
                                                 RolePermissionFeignService rolePermissionFeignService) {
        return new ArchAuthoritiesServiceImpl(roleMenuFeignService,
                                              roleGroupFeignService,
                                              roleResourceFeignService,
                                              rolePermissionFeignService);
    }

}
