package org.arch.ums.feign.account.client;


import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RoleResourceRequest;
import org.arch.ums.account.dto.RoleResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.feign.common.service.RolePermissionFeignService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleResourceService}服务远程调用的 feign 客户端.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:34
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-roleResource", path = "/ums/account/role/resource",
        configuration = DeFaultFeignConfig.class)
public interface UmsAccountRoleResourceFeignService extends RolePermissionFeignService<Resource, Long,
                                                        RoleResourceSearchDto, RoleResourceRequest, Long> {

}
