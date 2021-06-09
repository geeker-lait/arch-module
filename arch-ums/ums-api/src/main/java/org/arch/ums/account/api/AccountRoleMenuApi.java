package org.arch.ums.account.api;

import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.dto.RoleMenuRequest;
import org.arch.ums.account.dto.RoleMenuSearchDto;
import org.arch.ums.common.service.RolePermissionApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleMenuService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-roleMenu", path = "/ums/account/role/menu",
        configuration = DeFaultFeignConfig.class)
public interface AccountRoleMenuApi extends RolePermissionApi<MenuSearchDto, Long,
        RoleMenuSearchDto, RoleMenuRequest, Long> {

}
