package org.arch.ums.feign.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RolePermissionService}服务远程调用的 feign 客户端.
 * 注意: 与 auth-rbac 模块的 {@code RolePermissionFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:33
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rolePermission", path = "/ums/account/role/permission",
        configuration = UmsDeFaultFeignConfig.class)
public interface UmsAccountRolePermissionFeignService extends BaseFeignService<RolePermission, java.lang.Long> {

}
