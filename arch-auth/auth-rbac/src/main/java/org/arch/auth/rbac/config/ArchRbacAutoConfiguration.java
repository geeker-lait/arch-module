package org.arch.auth.rbac.config;

import org.arch.auth.rbac.feign.MenuApi;
import org.arch.auth.rbac.feign.PermissionApi;
import org.arch.auth.rbac.feign.ResourceApi;
import org.arch.auth.rbac.feign.RoleApi;
import org.arch.auth.rbac.feign.RoleGroupApi;
import org.arch.auth.rbac.feign.RoleMenuApi;
import org.arch.auth.rbac.feign.RolePermissionApi;
import org.arch.auth.rbac.feign.RoleResourceApi;
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
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.dcenter.ums.security.core.api.permission.service.AbstractUriAuthorizeService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.permission.config.PermissionAutoConfiguration;

/**
 * RBAC 权限服务自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.18 17:38
 */
@Configuration
@EnableScheduling
@AutoConfigureBefore({PermissionAutoConfiguration.class})
public class ArchRbacAutoConfiguration {

    @Bean
    public AbstractUriAuthorizeService abstractUriAuthorizeService(TenantContextHolder tenantContextHolder,
                                                                   AuthoritiesService authoritiesService) {
        return new ArchRbacUriAuthorizeServiceImpl(tenantContextHolder,
                                                   authoritiesService);
    }

    @Configuration
    @ConditionalOnMissingBean(type = "org.arch.auth.rbac.service.AuthoritiesService")
    @EnableFeignClients(basePackageClasses = {RoleResourceApi.class, RolePermissionApi.class,
            RoleMenuApi.class, RoleGroupApi.class, MenuApi.class,
            PermissionApi.class, ResourceApi.class, RoleApi.class})
    static class FeignAutoConfiguration {

        @Bean
        public AuthoritiesService authoritiesService(RoleMenuApi roleMenuApi,
                                                     RoleGroupApi roleGroupApi,
                                                     RoleResourceApi roleResourceApi,
                                                     RolePermissionApi rolePermissionApi,
                                                     MenuApi menuApi,
                                                     RoleApi roleApi,
                                                     PermissionApi permissionApi,
                                                     ResourceApi resourceApi) {

            return new FeignAuthoritiesServiceImpl(roleMenuApi,
                                                   roleGroupApi,
                                                   roleResourceApi,
                                                   rolePermissionApi,
                                                   menuApi,
                                                   roleApi,
                                                   permissionApi,
                                                   resourceApi);
        }

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
