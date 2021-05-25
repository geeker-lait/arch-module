package org.arch.ums.account.client;


import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RolePermissionRequest;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.common.service.RolePermissionFeignService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RolePermissionService}服务远程调用的 feign 客户端.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:33
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rolePermission", path = "/ums/account/role/permission",
        configuration = DeFaultFeignConfig.class)
public interface AccountRolePermissionFeignService extends RolePermissionFeignService<Permission, Long,
                                                                RolePermissionSearchDto, RolePermissionRequest, Long> {

}