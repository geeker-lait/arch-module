package org.arch.admin.config;

import org.arch.auth.rbac.config.ArchRbacAutoConfiguration.RbacMqBindingAutoConfiguration;
import org.arch.auth.rbac.config.ArchRbacAutoConfiguration.RbacPermissionUpdatedAutoConfiguration;
import org.arch.auth.rbac.stream.channel.RbacSource;
import org.arch.auth.rbac.stream.listener.RbacPermissionsUpdatedEventListener;
import org.arch.auth.rbac.stream.service.RbacMqReceiverOrSenderService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rbac and spring cloud stream  auto configuration
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.19 9:46
 */
@Configuration
@AutoConfigureBefore({RbacMqBindingAutoConfiguration.class})
@EnableBinding({RbacSource.class})
public class AuthSsoRbacMqStreamAutoConfiguration {

    @Configuration
    @AutoConfigureAfter({RbacPermissionUpdatedAutoConfiguration.class})
    static class AuthSsoRbacMqStreamListenerAutoConfiguration {

        @Bean
        public RbacPermissionsUpdatedEventListener rocketMqRolePermissionsUpdatedListener(
                RbacMqReceiverOrSenderService rbacMqReceiverOrSenderService) {
            return new RbacPermissionsUpdatedEventListener(rbacMqReceiverOrSenderService);
        }
    }
}
