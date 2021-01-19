package org.arch.auth.rbac.config;

import org.arch.auth.rbac.service.ArchRbacAuthorizeServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.dcenter.ums.security.core.api.premission.service.AbstractUriAuthorizeService;
import top.dcenter.ums.security.core.premission.config.PermissionAutoConfiguration;

/**
 * RBAC 权限服务自动配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.18 17:38
 */
@Configuration
@AutoConfigureBefore({PermissionAutoConfiguration.class})
public class ArchRbacAutoConfiguration {
    @Bean
    public AbstractUriAuthorizeService abstractUriAuthorizeService() {
        return new ArchRbacAuthorizeServiceImpl();
    }
}
