package org.arch.auth.rbac.service;

import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.Map;
import java.util.Set;

/**
 * 获取基于 RBAC 的所有权限资源服务
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 22:17
 */
public interface AuthoritiesService {

    /**
     * 获取所有角色的所有的权限资源
     * @return  Map(role, map(uri/path, Set(permission))
     */
    @NonNull
    Map<String, Map<String, Set<String>>> getAllAuthoritiesOfAllRole();

    /**
     * 获取 所有多租户 的所有角色的权限资源
     * @return  Map(tenantAuthority, Map(role, map(uri/path, Set(permission)))
     */
    @NonNull
    Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllTenant();

    /**
     * 获取 所有 scopes 的所有角色的权限资源
     * @return  Map(scopeAuthority, Map(role, map(uri/path, Set(permission)))
     */
    @NonNull
    Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllScopes();

    /**
     * 获取 所有 group 的所有角色资源
     * @return  Map(groupAuthority, Set(roleAuthority))
     */
    @NonNull
    Map<String, Set<String>> getAllRolesOfAllGroups();

    /**
     * 多租户 获取 所有 group 的所有角色资源
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
     */
    @NonNull
    Map<String, Map<String, Set<String>>> getAllGroupRolesOfAllTenant();

    /**
     * 获取 所有 group 的所有角色资源
     * @param groupId   用户的 groupId
     * @param roleIds   用户的角色 ids
     * @return  Map(groupAuthority, Set(roleAuthority))
     */
    @NonNull
    Map<String, Set<String>> getGroupRolesByGroupId(@NonNull Long groupId, Long... roleIds);

    /**
     * 多租户获取 所有 group 的所有角色资源
     *
     * @param tenantId 多租户 ID
     * @param groupId  用户的 groupId
     * @param roleIds  用户的角色 ids
     * @return Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
     */
    @NonNull
    Map<String, Map<String, Set<String>>> getGroupRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                                         @NonNull Long groupId,
                                                                         Long... roleIds);

    /**
     * 获取角色(roleId)所拥有的 resourceIds 资源信息缓存.
     *
     * @param roleId        角色 Id
     * @param resourceClass 资源 class
     * @param resourceIds   资源 Ids
     * @return Map(role, Map(uri/path, Set(permission)))
     * @throws RolePermissionsException 获取角色资源信息失败
     */
    @NonNull
    Map<String, Map<String, Set<String>>> getAuthoritiesByRoleId(@NonNull Long roleId, @NonNull Class<?> resourceClass,
                                                                 Long... resourceIds) throws RolePermissionsException;

    /**
     * 获取多租户的角色(roleId)所拥有的 resourceIds 资源信息缓存.
     *
     * @param tenantId      多租户 ID
     * @param roleId        角色 Id
     * @param resourceClass 资源 class
     * @param resourceIds   资源 Ids
     * @return Map(tenantAuthority, Map ( role, map ( uri / path, Set ( permission)))
     * @throws RolePermissionsException 获取缓存角色资源信息失败
     */
    @NonNull
    Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfTenant(@NonNull Long tenantId,
                                                                                      @NonNull Long roleId,
                                                                                      @NonNull Class<?> resourceClass,
                                                                                      Long... resourceIds) throws RolePermissionsException;

    /**
     * 获取 scopeId 的角色(roleId)所拥有的资源信息缓存.
     *
     * @param scopeId       scope id
     * @param roleId        角色 Id
     * @param resourceClass 资源 class
     * @param resourceIds   资源 Ids
     * @return Map(scopeAuthority, Map ( role, map ( uri / path, Set ( permission)))
     * @throws RolePermissionsException 获取缓存角色资源信息失败
     */
    @NonNull
    Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                                                       @NonNull Long roleId,
                                                                                       @NonNull Class<?> resourceClass,
                                                                                       Long... resourceIds) throws RolePermissionsException;

}
