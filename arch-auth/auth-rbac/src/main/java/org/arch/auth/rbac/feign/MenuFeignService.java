package org.arch.auth.rbac.feign;


import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.entity.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * arch-ums-api {@code MenuService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@code UmsAccountMenuFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:03:23
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-menu", path = "/ums/account/menu",
        configuration = DeFaultFeignConfig.class)
public interface MenuFeignService extends BaseFeignService<Menu, Long> {

    /**
     * 多租户根据 {@code menuIds} 获取 {@link Menu} 列表.
     * @param tenantId  多租户 ID
     * @param menuIds   菜单 ID 列表
     * @return  菜单列表
     */
    @GetMapping("/findByMenuIds/{tenantId}")
    @NonNull
    Response<List<Menu>> findByMenuIds(@PathVariable(value = "tenantId") Integer tenantId,
                                       @RequestBody List<Long> menuIds);
}
