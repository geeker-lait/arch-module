package org.arch.auth.rbac.service;

import org.arch.ums.account.vo.MenuVo;
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
     * 多租户 获取 所有 group 的所有角色资源
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
     */
    @NonNull
    Map<String, Map<String, Set<String>>> getAllGroupRolesOfAllTenant();

    /**
     * 多租户获取指定的角色组信息
     *
     * @param tenantId 多租户 ID
     * @param groupId  用户的 groupId
     * @param roleIds  用户的角色 ids
     * @return Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
     */
    @NonNull
    Map<String, Map<String, Set<String>>> getGroupRolesByGroupIdOfTenant(@NonNull Integer tenantId,
                                                                         @NonNull Long groupId,
                                                                         Long... roleIds);

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
    Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfTenant(@NonNull Integer tenantId,
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

    /**
     * 根据 tenantId 与 roleId 获取指定角色的菜单权限, 此接口适用于 菜单 与 权限分开设计的模型.
     *
     * @param tenantId 多租户权限
     * @param roleId   用户的角色权限
     * @param menuIds  用户的菜单权限集合
     * @return 指定租户与角色的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @NonNull
    Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> getMenuByRoleOfTenant(@NonNull Integer tenantId,
                                                                           @NonNull Long roleId,
                                                                           Long... menuIds);

    /**
     * 获取所有租户的菜单权限
     * @return 所拥有的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @NonNull
    Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> getAllMenuOfAllTenant();
}
