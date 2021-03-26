package org.arch.ums.feign.account.client;

import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.account.vo.MenuVo;
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
 * arch-ums-api {@code RoleMenuService}服务远程调用的 feign 客户端.
 * 注意: 与 auth-rbac 模块的 {@code RoleMenuFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-roleMenu", path = "/ums/account/role/menu",
        configuration = DeFaultFeignConfig.class)
public interface UmsAccountRoleMenuFeignService extends BaseFeignService<OauthToken, Long> {

    /**
     * 根据 tenantId 与 tenantId 获取指定角色的菜单权限, 此接口适用于 菜单 与 权限分开设计的模型.
     *
     * @param tenantId 多租户权限
     * @param roleId   用户的角色权限
     * @param menuIds  菜单权限集合
     * @return roleAuthority   所拥有的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @GetMapping("/find/{tenantId}/{roleId}")
    @NonNull
    Response<Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>>> findMenuByRoleOfTenant(@PathVariable(value = "tenantId") Integer tenantId,
                                                                                        @PathVariable(value = "roleId") Long roleId,
                                                                                        @RequestBody List<Long> menuIds);

    /**
     * 获取所有租户的菜单权限
     * @return roleAuthority   所拥有的所有菜单权限集合,
     *                         Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @GetMapping("/listAllMenu")
    @NonNull
    Response<Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>>> listAllMenuOfAllTenant();
}
