package org.arch.auth.rbac.config;

import org.arch.auth.rbac.feign.RoleGroupFeignService;
import org.arch.auth.rbac.feign.RoleMenuFeignService;
import org.arch.auth.rbac.feign.RolePermissionFeignService;
import org.arch.auth.rbac.feign.RoleResourceFeignService;
import org.arch.auth.rbac.service.ArchRbacUriAuthorizeServiceImpl;
import org.arch.auth.rbac.service.AuthoritiesService;
import org.arch.auth.rbac.service.FeignAuthoritiesServiceImpl;
import org.arch.auth.rbac.stream.channel.RbacSink;
import org.arch.auth.rbac.stream.channel.RbacSource;
import org.arch.auth.rbac.stream.event.RbacRemotePermissionUpdatedEvent;
import org.arch.auth.rbac.stream.service.RbacMqReceiverOrSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
@AutoConfigureAfter({ArchRbacFeignAutoConfiguration.class})
@AutoConfigureBefore({PermissionAutoConfiguration.class})
public class ArchRbacAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(type = "org.arch.auth.rbac.service.AuthoritiesService")
    public AuthoritiesService authoritiesService(RoleMenuFeignService roleMenuFeignService,
                                                 RoleGroupFeignService roleGroupFeignService,
                                                 RoleResourceFeignService roleResourceFeignService,
                                                 RolePermissionFeignService rolePermissionFeignService) {
        return new FeignAuthoritiesServiceImpl(roleMenuFeignService,
                                               roleGroupFeignService,
                                               roleResourceFeignService,
                                               rolePermissionFeignService);
    }

    @Bean
    public AbstractUriAuthorizeService abstractUriAuthorizeService(TenantContextHolder tenantContextHolder,
                                                                   AuthoritiesService authoritiesService) {
        return new ArchRbacUriAuthorizeServiceImpl(tenantContextHolder,
                                                   authoritiesService);
    }

    /**
     * {@link RbacRemotePermissionUpdatedEvent} 自动配置
     */
    @Configuration
    @AutoConfigureBefore({PermissionAutoConfiguration.class})
    @EnableBinding({RbacSink.class})
    public static class RbacMqBindingAutoConfiguration {

    }

    /**
     * 权限更新自动配置
     */
    @Configuration
    @AutoConfigureAfter({RbacMqBindingAutoConfiguration.class})
    public static class RbacPermissionUpdatedAutoConfiguration {

        @Bean
        public RbacMqReceiverOrSenderService rbacMqReceiverOrSenderService(@Autowired(required = false) RbacSource rbacSource) {
            return new RbacMqReceiverOrSenderService(rbacSource);
        }

    }

}
