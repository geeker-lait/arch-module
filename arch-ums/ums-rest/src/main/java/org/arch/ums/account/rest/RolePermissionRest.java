package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.dto.RolePermissionRequest;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号-角色权限表(RolePermission) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:52:17
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/role/permission")
public interface RolePermissionRest extends CrudRest<RolePermissionRequest, java.lang.Long, RolePermissionSearchDto> {

    /**
     * 基于多租户, 更新角色 {@code roleId} 的 权限资源
     *
     * @param tenantId      多租户 ID
     * @param roleId        角色 ID
     * @param permissionIds 权限资源 ids
     * @return 是否更新成功
     */
    @PutMapping("/updateOfTenant/{tenantId}/{roleId}")
    @NonNull
    Boolean updateResourcesByRoleIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                            @PathVariable(value = "roleId") Long roleId,
                                            @RequestBody List<Long> permissionIds);

    /**
     * 基于 SCOPE, 更新角色 {@code roleId} 的 权限资源
     *
     * @param scopeId       SCOPE ID
     * @param roleId        角色 ID
     * @param permissionIds 权限资源 ids
     * @return 是否更新成功
     */
    @PutMapping("/updateOfScope/{scopeId}/{roleId}")
    @NonNull
    Boolean updateResourcesByRoleIdOfScopeId(@PathVariable(value = "scopeId") Long scopeId,
                                             @PathVariable(value = "roleId") Long roleId,
                                             @RequestBody List<Long> permissionIds);

    /**
     * 基于多租户, 查询指定角色 {@code roleId} 的权限资源列表
     *
     * @param tenantId 多租户 ID
     * @param roleId   角色 ID
     * @return 权限资源列表
     */
    @GetMapping("/findOfTenant/{tenantId}/{roleId}")
    @NonNull
    List<PermissionSearchDto> findAllResourcesByRoleIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                               @PathVariable(value = "roleId") Long roleId);

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     *
     * @param scopeId SCOPE ID
     * @param roleId  角色 ID
     * @return 权限资源列表
     */
    @GetMapping("/findOfScope/{scopeId}/{roleId}")
    @NonNull
    List<PermissionSearchDto> findAllResourcesByRoleIdOfScopeId(@PathVariable(value = "scopeId") Long scopeId,
                                                                @PathVariable(value = "roleId") Long roleId);

}
