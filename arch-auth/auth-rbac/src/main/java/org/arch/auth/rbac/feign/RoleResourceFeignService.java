package org.arch.auth.rbac.feign;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.feign.account.client.UmsAccountRoleResourceFeignService;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleResourceService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@link UmsAccountRoleResourceFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:34
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-roleResource", path = "/ums/account/role/resource",
        configuration = UmsDeFaultFeignConfig.class)
public interface RoleResourceFeignService extends BaseFeignService<RoleResource, Long> {
}
