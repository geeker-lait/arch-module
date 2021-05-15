package org.arch.auth.rbac.feign;


import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.RoleGroupRequest;
import org.arch.ums.account.dto.RoleGroupSearchDto;
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
 * 注意: 与 ums-feign-api 模块的 {@code UmsAccountRoleGroupFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:34
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-roleGroup", path = "/ums/account/role/group",
        configuration = DeFaultFeignConfig.class)
public interface RoleGroupFeignService extends BaseFeignService<RoleGroupSearchDto, RoleGroupRequest, Long> {

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
