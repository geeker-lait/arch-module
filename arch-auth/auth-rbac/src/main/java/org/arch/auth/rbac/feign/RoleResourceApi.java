package org.arch.auth.rbac.feign;

import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RoleResourceRequest;
import org.arch.ums.account.dto.RoleResourceSearchDto;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * arch-ums-api {@code RoleResourceService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-api 模块的 {@code UmsAccountRoleResourceFeignService} 逻辑相同, 添加接口时需一同修改.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:34
 * @since 1.0.0
 */
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-roleResource", path = "/ums/account/role/resource",
        configuration = DeFaultFeignConfig.class)
public interface RoleResourceApi extends FeignApi<RoleResourceSearchDto, RoleResourceRequest, Long> {

}
