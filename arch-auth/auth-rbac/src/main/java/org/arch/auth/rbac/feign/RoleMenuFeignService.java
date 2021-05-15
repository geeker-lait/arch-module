package org.arch.auth.rbac.feign;

import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RoleMenuRequest;
import org.arch.ums.account.dto.RoleMenuSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleMenuService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@code UmsAccountRoleMenuFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-roleMenu", path = "/ums/account/role/menu",
        configuration = DeFaultFeignConfig.class)
public interface RoleMenuFeignService extends BaseFeignService<RoleMenuSearchDto, RoleMenuRequest, Long> {

}
