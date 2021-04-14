package org.arch.ums.feign.account.client;


import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.RoleGroup;
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
 * arch-ums-api {@code RoleGroupService}服务远程调用的 feign 客户端.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:32
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-roleGroup", path = "/ums/account/role/group",
        configuration = DeFaultFeignConfig.class)
public interface UmsAccountRoleGroupFeignService extends BaseFeignService<RoleGroup, java.lang.Long> {

    /**
     * 获取所有租户的所有角色组的角色
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @GetMapping("/listGroups")
    @NonNull
    Response<Map<String, Map<String, Set<String>>>> listAllGroups();

    /**
     * 多租户获取 所有 group 的所有角色资源
     *
     * @param tenantId 多租户 ID
     * @param groupId  用户的 groupId
     * @param roleIds  用户的角色 ids
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @GetMapping("/find/{tenantId}/{groupId}")
    @NonNull
    Response<Map<String, Map<String, Set<String>>>> findGroupRolesByGroupIdOfTenant(
                                                                    @PathVariable(value = "tenantId") Integer tenantId,
                                                                    @PathVariable(value = "groupId") Long groupId,
                                                                    @RequestBody List<Long> roleIds);
}
