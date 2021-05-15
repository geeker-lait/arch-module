package org.arch.auth.rbac.feign;


import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.dto.RoleSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@code UmsAccountRoleFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:32
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-role", path = "/ums/account/role",
        configuration = DeFaultFeignConfig.class)
public interface RoleFeignService extends BaseFeignService<RoleSearchDto, RoleRequest, Long> {

}
