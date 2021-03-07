package org.arch.auth.rbac.config;

import org.arch.auth.rbac.feign.RoleGroupFeignService;
import org.arch.auth.rbac.feign.RoleMenuFeignService;
import org.arch.auth.rbac.feign.RolePermissionFeignService;
import org.arch.auth.rbac.feign.RoleResourceFeignService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 23:21
 */
@Configuration
@EnableFeignClients(basePackageClasses = {RoleResourceFeignService.class,
        RolePermissionFeignService.class, RoleMenuFeignService.class, RoleGroupFeignService.class})
public class ArchRbacFeignAutoConfiguration {

}
