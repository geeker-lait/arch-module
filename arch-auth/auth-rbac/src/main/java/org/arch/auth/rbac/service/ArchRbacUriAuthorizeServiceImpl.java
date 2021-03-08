package org.arch.auth.rbac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.api.premission.service.AbstractUriAuthorizeService;
import top.dcenter.ums.security.core.api.premission.service.UpdateCacheOfRolesResourcesService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.RolePermissionsException;
import top.dcenter.ums.security.core.premission.enums.PermissionType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static top.dcenter.ums.security.common.consts.TenantConstants.DEFAULT_TENANT_PREFIX;

/**
 * RBAC 权限服务
 * @see AbstractUriAuthorizeService
 * @author YongWu zheng
 * @since 2020.12.29 20:21
 */
@RequiredArgsConstructor
public class ArchRbacUriAuthorizeServiceImpl extends AbstractUriAuthorizeService implements UpdateCacheOfRolesResourcesService, InitializingBean {

    /**
     * 所有角色 uri(资源) 权限 Map(tenantAuthority/scopeAuthority, Map(role, map(uri/path, Set(permission)))
     */
    private volatile Map<String, Map<String, Map<String, Set<String>>>> allTenantsAuthoritiesMap;

    /**
     * 所有组的角色 Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
     */
    private volatile Map<String, Map<String, Set<String>>> allTenantsGroupsMap;

    private final TenantContextHolder tenantContextHolder;

    private final AuthoritiesService authoritiesService;

    private final Object lock = new Object();

    private volatile Boolean isUpdatedOfAllScopes = Boolean.FALSE;
    private volatile Boolean isUpdatedOfAllRoles = Boolean.FALSE;
    private volatile Boolean isUpdatedOfAllGroups = Boolean.FALSE;

    /**
     * @see #updateAuthoritiesOfAllTenant()
     */
    @Override
    protected void updateAuthoritiesOfAllRoles() {
        updateAuthoritiesOfAllTenant();
    }

    @Override
    protected void updateAuthoritiesOfAllTenant() {
        if (this.allTenantsAuthoritiesMap != null) {
            // 从数据源获取所有角色的权限
            if (!this.isUpdatedOfAllRoles) {
                synchronized (lock) {
                    if (!this.isUpdatedOfAllRoles) {
                        this.allTenantsAuthoritiesMap.putAll(authoritiesService.getAllAuthoritiesOfAllTenant());
                        this.isUpdatedOfAllRoles = Boolean.TRUE;
                    }
                }
            }
            return;
        }
        synchronized (lock) {
            if (this.allTenantsAuthoritiesMap != null) {
                if (!this.isUpdatedOfAllRoles) {
                    this.allTenantsAuthoritiesMap.putAll(authoritiesService.getAllAuthoritiesOfAllTenant());
                    this.isUpdatedOfAllRoles = Boolean.TRUE;
                }
                return;
            }
            // 从数据源获取所有角色的权限
            this.allTenantsAuthoritiesMap = new ConcurrentHashMap<>(authoritiesService.getAllAuthoritiesOfAllTenant());
        }
    }

    @Override
    protected void updateAuthoritiesOfAllScopes() {
        if (this.allTenantsAuthoritiesMap != null) {
            // 从数据源获取所有 scopes 的权限
            if (!this.isUpdatedOfAllScopes) {
                synchronized (lock) {
                    if (!this.isUpdatedOfAllScopes) {
                        this.allTenantsAuthoritiesMap.putAll(authoritiesService.getAllAuthoritiesOfAllScopes());
                        this.isUpdatedOfAllScopes = Boolean.TRUE;
                    }
                }
            }
            return;
        }
        synchronized (lock) {
            if (this.allTenantsAuthoritiesMap != null) {
                if (!this.isUpdatedOfAllScopes) {
                    this.allTenantsAuthoritiesMap.putAll(authoritiesService.getAllAuthoritiesOfAllScopes());
                    this.isUpdatedOfAllScopes = Boolean.TRUE;
                }
                return;
            }
            // 从数据源获取所有 scopes 的权限
            this.allTenantsAuthoritiesMap = new ConcurrentHashMap<>(authoritiesService.getAllAuthoritiesOfAllScopes());
        }
    }

    protected void updateAllGroupsOfAllTenant() {
        if (this.allTenantsGroupsMap != null) {
            // 从数据源获取所有角色的权限
            if (!this.isUpdatedOfAllGroups) {
                synchronized (lock) {
                    if (!this.isUpdatedOfAllGroups) {
                        this.allTenantsGroupsMap.putAll(authoritiesService.getAllGroupRolesOfAllTenant());
                        this.isUpdatedOfAllGroups = Boolean.TRUE;
                    }
                }
            }
            return;
        }
        synchronized (lock) {
            if (this.allTenantsGroupsMap != null) {
                if (!this.isUpdatedOfAllGroups) {
                    this.allTenantsGroupsMap.putAll(authoritiesService.getAllGroupRolesOfAllTenant());
                    this.isUpdatedOfAllGroups = Boolean.TRUE;
                }
                return;
            }
            // 从数据源获取所有角色的权限
            this.allTenantsGroupsMap = new ConcurrentHashMap<>(authoritiesService.getAllGroupRolesOfAllTenant());
        }
    }

    /**
     * 返回当前租户的根据 groupAuthority 获取 group 所拥有的所有角色, 推荐通过 {@link #getRolesByGroupOfTenant(String, String)} 获取.
     * @param groupAuthority    用户的 group 权限
     * @return  group 所拥有的所有角色集合
     */
    @NonNull
    @Override
    public Set<String> getRolesByGroup(@NonNull String groupAuthority) {
        String tenantId = tenantContextHolder.getTenantId();
        return getRolesByGroupOfTenant(DEFAULT_TENANT_PREFIX + tenantId, groupAuthority);
    }

    @NonNull
    @Override
    public Set<String> getRolesByGroupOfTenant(@NonNull String tenantAuthority, @NonNull String groupAuthority) {
        if (this.allTenantsGroupsMap != null) {
            return getRolesByGroupAndTenant(tenantAuthority, groupAuthority);
        }
        updateAllGroupsOfAllTenant();
        return getRolesByGroupAndTenant(tenantAuthority, groupAuthority);
    }

    /**
     * 返回当前租户的角色的 uri 的权限 map. 推荐调用 {@link #getRolesAuthoritiesOfTenant(String)} 方法<br>
     *     返回值为: Map(role, Map(uri, UriResourcesDTO))
     * @return Map(String, Map(String, String)) 的 key 为必须包含"ROLE_"前缀的角色名称(如: ROLE_ADMIN), value 为 UriResourcesDTO map
     * (key 为 uri, 此 uri 可以为 antPath 通配符路径,如 /user/**; value 为 UriResourcesDTO).
     */
    @Override
    @NonNull
    public Map<String, Map<String, Set<String>>> getRolesAuthorities() {
        String tenantId = tenantContextHolder.getTenantId();
        return getRolesAuthoritiesOfTenant(DEFAULT_TENANT_PREFIX + tenantId);
    }

    /**
     * 获取 指定租户 所有角色的 uri(资源) 的权限 Map(role, Map(uri, Set(permission))).<br>
     * <pre>
     * // 当为 restful 风格的 Api 时, uri 与 permission 是一对一关系:
     *  uri         permission
     *  /user/*     list
     *  /user/*     add
     *  /user/*     edit
     *  /user/*     delete
     *
     * // 当不是 restful 风格的 Api 时, uri 与 permission 可能是一对一关系, 也可能是一对多关系:
     *  uri         permission
     *  /user/*     list
     *  /user/*     add,edit
     *  /user/*     delete
     *
     * // 但最终返回的结果时是一样的; Map{["user/*", Set[list,add,edit,delete]]..}
     * </pre>
     * @param tenantAuthority   包含 TENANT_ 前缀的租户权限, 例如: TENANT_租户ID
     * @return                  Map(role, Map(uri, Set(permission))): <br>
     *     key: 必须包含"ROLE_"前缀的角色名称(如: ROLE_ADMIN), <br>
     *     value:
     *          map(key 为 uri, 此 uri 可以为 antPath 通配符路径,如 /user/**;
     *          value 为权限字符串({@link PermissionType#getPermission()}) Set).
     */
    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getRolesAuthoritiesOfTenant(@NonNull String tenantAuthority) {
        if (this.allTenantsAuthoritiesMap != null) {
            return this.allTenantsAuthoritiesMap.get(tenantAuthority);
        }
        updateAuthoritiesOfAllTenant();
        return this.allTenantsAuthoritiesMap.get(tenantAuthority);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getScopeAuthoritiesOfScope(@NonNull Set<String> scopeAuthoritySet) {
        final Map<String, Map<String, Set<String>>> scopeMap = new HashMap<>(16);
        if (this.allTenantsAuthoritiesMap != null) {
            scopeAuthoritySet.forEach((scopeAuthority) ->
                                              scopeMap.putAll(this.allTenantsAuthoritiesMap.get(scopeAuthority))
            );
            return scopeMap;
        }
        updateAuthoritiesOfAllScopes();
        scopeAuthoritySet.forEach((scopeAuthority) ->
                                          scopeMap.putAll(this.allTenantsAuthoritiesMap.get(scopeAuthority))
        );
        return scopeMap;
    }

    @Override
    public void afterPropertiesSet() {
        // 缓存所有 uri(资源) 权限 Map(tenantAuthority/scopeAuthority, Map(role, map(uri/path, Set(permission)))
        // 角色组(Group) Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
        initUpdateAllAuthorities();
    }

    @Override
    public boolean updateAuthoritiesByRoleId(@NonNull Long roleId, @NonNull Class<?> resourceClass,
                                             Long... resourceIds) throws RolePermissionsException {
        throw new RuntimeException("arch 为多租户应用, 不支持此方法更新角色权限; 使用 updateAuthoritiesByRoleIdOfTenant() 方法");
    }

    @Override
    public boolean updateAuthoritiesByRoleIdOfTenant(@NonNull Long tenantId, @NonNull Long roleId,
                                                     @NonNull Class<?> resourceClass,
                                                     Long... resourceIds) throws RolePermissionsException {
        // Map(tenantAuthority, Map ( role, map ( uri / path, Set ( permission)))
        Map<String, Map<String, Map<String, Set<String>>>> authoritiesByRoleIdOfTenant =
                this.authoritiesService.getAuthoritiesByRoleIdOfTenant(tenantId, roleId, resourceClass, resourceIds);
        updateCacheAuthoritiesByTenantIdOrScopeId(authoritiesByRoleIdOfTenant);
        return true;
    }

    @Override
    public boolean updateAuthoritiesByRoleIdOfScopeId(@NonNull Long scopeId, @NonNull Long roleId,
                                                      @NonNull Class<?> resourceClass,
                                                      Long... resourceIds) throws RolePermissionsException {
        // Map(scopeAuthority, Map ( role, map ( uri / path, Set ( permission)))
        Map<String, Map<String, Map<String, Set<String>>>> authoritiesByRoleIdOfTenant =
                this.authoritiesService.getAuthoritiesByRoleIdOfScopeId(scopeId, roleId, resourceClass, resourceIds);
        updateCacheAuthoritiesByTenantIdOrScopeId(authoritiesByRoleIdOfTenant);
        return true;
    }

    @Override
    public boolean updateRolesByGroupId(@NonNull Long groupId, Long... roleIds) throws RolePermissionsException {
        throw new RuntimeException("arch 为多租户应用, 不支持此方法更新 group 所拥有的所有角色; 使用 updateRolesByGroupIdOfTenant 方法");
    }

    @Override
    public boolean updateRolesByGroupIdOfTenant(@NonNull Long tenantId, @NonNull Long groupId,
                                                Long... roleIds) throws RolePermissionsException {
        // Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
        Map<String, Map<String, Set<String>>> tenantGroupRolesMap =
                this.authoritiesService.getGroupRolesByGroupIdOfTenant(tenantId, groupId, roleIds);
        updateCacheGroupRolesByTenantIdAndGroupId(tenantGroupRolesMap);
        return true;
    }

    @NonNull
    private Set<String> getRolesByGroupAndTenant(@NonNull String tenantAuthority, @NonNull String groupAuthority) {
        Map<String, Set<String>> tenantGroupRolesMap = this.allTenantsGroupsMap.get(tenantAuthority);
        if (isNull(tenantGroupRolesMap)) {
            return Collections.emptySet();
        }
        return tenantGroupRolesMap.get(groupAuthority);
    }

    private void updateCacheGroupRolesByTenantIdAndGroupId(@NonNull Map<String, Map<String, Set<String>>>
                                                                  tenantGroupRolesMap) {
        // tenantGroupRolesMap = Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
        tenantGroupRolesMap.forEach((tenantAuthority, updateGroupMap) -> {
            // updateGroupMap = Map(groupAuthority, Set(roleAuthority))
            this.allTenantsGroupsMap.compute(tenantAuthority, (key, groupMap) -> {
                // groupMap = Map(groupAuthority, Set(roleAuthority))
                if (isNull(groupMap)) {
                    return updateGroupMap;
                }
                updateGroupMap.forEach((groupAuthority, roleAuthoritySet) -> {
                    //noinspection Convert2MethodRef
                    groupMap.put(groupAuthority, roleAuthoritySet);
                });
                return groupMap;
            });
        });

    }

    private void updateCacheAuthoritiesByTenantIdOrScopeId(@NonNull Map<String, Map<String, Map<String, Set<String>>>>
                                                                   tenantOrScopeGroupRolesMap) {

        // tenantOrScopeGroupRolesMap = Map(tenantOrScopeAuthority, Map(role, map(uri/path, Set(permission)))
        tenantOrScopeGroupRolesMap.forEach((tenantOrScopeAuthority, updateRoleMap) -> {
            // updateRoleMap = Map(role, map(uri/path, Set(permission))
            this.allTenantsAuthoritiesMap.compute(tenantOrScopeAuthority, (key, roleMap) -> {
                // roleMap = Map(role, map(uri/path, Set(permission))
                if (isNull(roleMap)) {
                    return updateRoleMap;
                }
                updateRoleMap.forEach((updateRole, updateUriMap) -> {
                    //noinspection Convert2MethodRef
                    roleMap.put(updateRole, updateUriMap);
                });
                return roleMap;
            });
        });
    }

    private void initUpdateAllAuthorities() {
        // 初始化更新所有的权限
        synchronized (lock) {
            this.isUpdatedOfAllRoles = Boolean.FALSE;
            this.isUpdatedOfAllScopes = Boolean.FALSE;
            this.isUpdatedOfAllGroups = Boolean.FALSE;
        }
        updateAuthoritiesOfAllTenant();
        updateAuthoritiesOfAllScopes();
        updateAllGroupsOfAllTenant();
    }

}
