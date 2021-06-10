package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.RoleGroupRequest;
import org.arch.ums.account.dto.RoleGroupSearchDto;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 账号-角色组织或机构(RoleGroup) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:52:07
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/role/group")
public interface RoleGroupRest extends CrudRest<RoleGroupRequest, java.lang.Long, RoleGroupSearchDto> {

    /**
     * 获取所有租户的所有角色组的角色
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @GetMapping("/listGroups")
    @NonNull
    Map<String, Map<String, Set<String>>> listAllGroups();

    /**
     * 多租户获取 所有 group 的所有角色资源
     *
     * @param tenantId 多租户 ID
     * @param groupId  用户的 groupId
     * @param roleIds  用户的角色 ids
     * @return Map(tenantAuthority, Map ( groupAuthority, Set ( roleAuthority))), 如果不存在这返回空集合.
     */
    @GetMapping("/find/{tenantId:\\d+}/{groupId:\\d+}")
    @NonNull
    Map<String, Map<String, Set<String>>> findGroupRolesByGroupIdOfTenant(
                                                    @PathVariable(value = "tenantId") Integer tenantId,
                                                    @PathVariable(value = "groupId") Long groupId,
                                                    @RequestBody List<Long> roleIds);

    /**
     * 基于多租户, 查询指定角色组 {@code groupId} 所拥有的所有角色集合, Set(roleAuthority).
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @return groupId 所拥有的所有角色集合, Set(roleAuthority).
     */
    @GetMapping("/findRoles/{tenantId}/{groupId}")
    @NonNull
    Set<String> findRolesByGroupIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                           @PathVariable(value = "groupId") Long groupId);

    /**
     * 基于多租户, 更新角色组 {@code groupId} 的角色集合
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @param roleIds  角色 ids
     * @return 是否更新成功
     */
    @PutMapping("/updateRoles/{tenantId}/{groupId}")
    @NonNull
    Boolean updateRolesByGroupIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                         @PathVariable(value = "groupId") Long groupId,
                                         @RequestBody List<Long> roleIds);
}

