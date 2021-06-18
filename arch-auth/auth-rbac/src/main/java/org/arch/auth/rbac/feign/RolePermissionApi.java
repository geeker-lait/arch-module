package org.arch.auth.rbac.feign;

import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RolePermissionRequest;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * arch-ums-api {@code RolePermissionService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-api 模块的 {@code UmsAccountRolePermissionFeignService} 逻辑相同, 添加接口时需一同修改.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:33
 * @since 1.0.0
 */
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-rolePermission", path = "/ums/account/role/permission",
        configuration = DeFaultFeignConfig.class)
public interface RolePermissionApi extends FeignApi<RolePermissionSearchDto, RolePermissionRequest, Long> {

}
