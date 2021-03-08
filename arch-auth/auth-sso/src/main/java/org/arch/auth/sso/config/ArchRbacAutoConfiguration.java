package org.arch.auth.sso.config;

import org.arch.auth.rbac.config.ArchRbacPermissionServiceAspectAutoConfiguration;
import org.arch.auth.sso.rbac.service.PublishUpdateMessageAndLocalRolePermissionsServiceAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.core.api.premission.service.RolePermissionsServiceAspect;

/**
 * sso 权限自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.7 22:30
 */
@Configuration
@AutoConfigureBefore({ArchRbacPermissionServiceAspectAutoConfiguration.class})
public class ArchRbacAutoConfiguration {

    @Bean
    public RolePermissionsServiceAspect rolePermissionsServiceAspect() {
        return new PublishUpdateMessageAndLocalRolePermissionsServiceAspect();
    }
}
