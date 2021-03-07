package org.arch.auth.rbac.feign;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.feign.account.client.UmsAccountRoleGroupFeignService;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleGroupService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@link UmsAccountRoleGroupFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:34
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-roleGroup", path = "/ums/account/role/group",
        configuration = UmsDeFaultFeignConfig.class)
public interface RoleGroupFeignService extends BaseFeignService<RoleGroup, Long> {
}
