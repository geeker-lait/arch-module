package org.arch.ums.feign.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.Role;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RoleService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:32
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-role", path = "/ums/account/role",
        configuration = UmsDeFaultFeignConfig.class)
public interface UmsAccountRoleFeignService extends BaseFeignService<Role, java.lang.Long> {

}
