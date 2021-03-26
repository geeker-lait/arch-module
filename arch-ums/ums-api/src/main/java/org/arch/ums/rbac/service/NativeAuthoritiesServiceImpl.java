package org.arch.ums.rbac.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.service.AuthoritiesService;
import org.arch.ums.account.service.RoleGroupService;
import org.arch.ums.account.service.RoleMenuService;
import org.arch.ums.account.service.RolePermissionService;
import org.arch.ums.account.service.RoleResourceService;
import org.arch.ums.account.vo.MenuVo;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 获取基于 RBAC 的所有权限资源的接口实现
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 22:24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NativeAuthoritiesServiceImpl implements AuthoritiesService {

    private final RoleMenuService menuService;
    private final RoleGroupService groupService;
    private final RoleResourceService resourceService;
    private final RolePermissionService permissionService;


    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllTenant() {

        Map<String, Map<String, Map<String, Set<String>>>> roleResourceMap =
                this.resourceService.listAllResourceAuthorities();
        Map<String, Map<String, Map<String, Set<String>>>> rolePermissionMap =
                this.permissionService.listAllPermissionAuthorities();

        int size = rolePermissionMap.size() + roleResourceMap.size();
        // Map(tenantAuthority, Map(role, map(uri/path, Set(permission)))
        final Map<String, Map<String, Map<String, Set<String>>>> allAuthorityMap = new HashMap<>(size);
        allAuthorityMap.putAll(roleResourceMap);
        allAuthorityMap.putAll(rolePermissionMap);
        return allAuthorityMap;
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllScopes() {
        // Map(scopeAuthority, Map(role, map(uri/path, Set(permission)))
        // TODO: 实现 auth-oauth 模块后再实现
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getAllGroupRolesOfAllTenant() {
        // Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
        return this.groupService.listAllGroups();
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getGroupRolesByGroupIdOfTenant(@NonNull Integer tenantId,
                                                                                @NonNull Long groupId,
                                                                                Long... roleIds) {
        try {
            // Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
            return this.groupService.findGroupRolesByGroupIdOfTenant(tenantId,
                                                                     groupId,
                                                                     Arrays.asList(roleIds));
        }
        catch (Exception e) {
            String msg = String.format("获取角色组信息失败: tenantId=%s, groupId=%s, roleIds=%s",
                                          tenantId, groupId, Arrays.toString(roleIds));
            log.error(msg, e);
            return new HashMap<>(0);
        }
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfTenant(@NonNull Integer tenantId,
                                                                                             @NonNull Long roleId,
                                                                                             @NonNull Class<?> resourceClass,
                                                                                             Long... resourceIds) throws RolePermissionsException {
        try {
            // Map(tenantAuthority, Map ( role, map ( uri / path, Set ( permission)))
            return getAuthoritiesByResourceClass(tenantId, roleId, resourceClass, resourceIds);
        }
        catch (Exception e) {
            String msg = String.format("获取角色权限信息失败: tenantId=%s, roleId=%s, resourceIds=%s",
                                       tenantId, roleId, Arrays.toString(resourceIds));
            log.error(msg, e);
            return new HashMap<>(0);
        }
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                                                              @NonNull Long roleId,
                                                                                              @NonNull Class<?> resourceClass,
                                                                                              Long... resourceIds) throws RolePermissionsException {
        // Map(scopeAuthority, Map ( role, map ( uri / path, Set ( permission)))
        // TODO: 实现 auth-oauth 模块后再实现
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> getMenuByRoleOfTenant(@NonNull Integer tenantId,
                                                                                 @NonNull Long roleId,
                                                                                 Long... menuIds) {

        try {
            // Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], List(Menu[sorted])))), 中括号中的排序字段.
            return this.menuService.findMenuByRoleOfTenant(tenantId,
                                                           roleId,
                                                           Arrays.asList(menuIds));
        }
        catch (Exception e) {
            String msg = String.format("获取角色菜单权限信息失败: tenantId=%s, roleId=%s", tenantId, roleId);
            log.error(msg, e);
            return new HashMap<>(0);
        }
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> getAllMenuOfAllTenant() {
        // Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], List(Menu[sorted])))), 中括号中的排序字段.
        return this.menuService.listAllMenuOfAllTenant();
    }

    private Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByResourceClass(@NonNull Integer tenantId,
                                                                                             @NonNull Long roleId,
                                                                                             @NonNull Class<?> resourceClass,
                                                                                             Long... resourceIds) {
        // Map(tenantAuthority, Map ( role, map ( uri / path, Set ( permission)))

        switch (resourceClass.getName()) {
            case "org.arch.ums.account.entity.Resource":
                return this.resourceService.findAuthoritiesByRoleIdOfTenant(tenantId,
                                                                            roleId,
                                                                            Arrays.asList(resourceIds));
            case "org.arch.ums.account.entity.Permission":
                return this.permissionService.findAuthoritiesByRoleIdOfTenant(tenantId,
                                                                              roleId,
                                                                              Arrays.asList(resourceIds));
            default:
                throw new RolePermissionsException(ErrorCodeEnum.PARAMETER_ERROR, "resourceClass");
        }

    }

}
