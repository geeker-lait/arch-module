package org.arch.auth.rbac.feign;


import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.RoleResource;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * arch-ums-api {@code RoleResourceService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@code UmsAccountRoleResourceFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:34
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-roleResource", path = "/ums/account/role/resource",
        configuration = DeFaultFeignConfig.class)
public interface RoleResourceFeignService extends BaseFeignService<RoleResource, Long> {

}
