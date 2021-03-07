package org.arch.auth.rbac.config;

import org.arch.auth.rbac.service.DistributedRolePermissionsServiceAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.core.api.premission.service.RolePermissionsServiceAspect;
import top.dcenter.ums.security.core.premission.config.PermissionAutoConfiguration;

/**
 * 分布式权限切面自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.7 22:23
 */
@Configuration
@AutoConfigureBefore({PermissionAutoConfiguration.class})
public class ArchRbacPermissionServiceAspectAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(type = "top.dcenter.ums.security.core.api.premission.service.RolePermissionsServiceAspect")
    public RolePermissionsServiceAspect rolePermissionsServiceAspect() {
        return new DistributedRolePermissionsServiceAspect();
    }
}
