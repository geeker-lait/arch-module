package org.arch.auth.rbac.service;

import lombok.RequiredArgsConstructor;
import org.arch.auth.rbac.feign.RoleGroupFeignService;
import org.arch.auth.rbac.feign.RoleMenuFeignService;
import org.arch.auth.rbac.feign.RolePermissionFeignService;
import org.arch.auth.rbac.feign.RoleResourceFeignService;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 获取基于 RBAC 的所有权限资源的接口实现
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 22:24
 */
@RequiredArgsConstructor
public class ArchAuthoritiesServiceImpl implements AuthoritiesService {

    private final RoleMenuFeignService roleMenuFeignService;
    private final RoleGroupFeignService roleGroupFeignService;
    private final RoleResourceFeignService roleResourceFeignService;
    private final RolePermissionFeignService rolePermissionFeignService;


    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getAllAuthoritiesOfAllRole() {
        throw new RuntimeException("arch 为多租户应用, 不支持此方法获取角色权限; 使用 getAllAuthoritiesOfAllTenant() 方法");
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllTenant() {
        // Map(tenantAuthority, Map(role, map(uri/path, Set(permission)))
        // todo
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllScopes() {
        // Map(scopeAuthority, Map(role, map(uri/path, Set(permission)))
        final Map<String, Map<String, Map<String, Set<String>>>> allAuthorityMap = new HashMap<>(1000);
        //  todo      umsAccountRoleMenuFeignService.list()
        // 从数据源获取所有角色的权限
        return allAuthorityMap;
    }

    @NonNull
    @Override
    public Map<String, Set<String>> getAllRolesOfAllGroups() {
        throw new RuntimeException("arch 为多租户应用, 不支持此方法获取 group 所拥有的所有角色; 使用 getAllGroupRolesOfAllTenant 方法");
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getAllGroupRolesOfAllTenant() {
        // Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
        // TODO
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Set<String>> getGroupRolesByGroupId(@NonNull Long groupId, Long... roleIds) {
        throw new RuntimeException("arch 为多租户应用, 不支持此方法更新 group 所拥有的所有角色; 使用 updateRolesByGroupIdOfTenant 方法");
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getGroupRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                                                @NonNull Long groupId,
                                                                                Long... roleIds) {
        // Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
        // TODO
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getAuthoritiesByRoleId(@NonNull Long roleId,
                                                                        @NonNull Class<?> resourceClass,
                                                                        Long... resourceIds) throws RolePermissionsException {
        throw new RuntimeException("arch 为多租户应用, 不支持此方法更新角色权限; 使用 updateAuthoritiesByRoleIdOfTenant() 方法");

    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfTenant(@NonNull Long tenantId,
                                                                                             @NonNull Long roleId,
                                                                                             @NonNull Class<?> resourceClass,
                                                                                             Long... resourceIds) throws RolePermissionsException {
        // Map(tenantAuthority, Map ( role, map ( uri / path, Set ( permission)))
        // TODO
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                                                              @NonNull Long roleId,
                                                                                              @NonNull Class<?> resourceClass,
                                                                                              Long... resourceIds) throws RolePermissionsException {
        // Map(scopeAuthority, Map ( role, map ( uri / path, Set ( permission)))
        // 从数据源获取所有 scopes 的权限, 目前不支持, 待实现
        return new HashMap<>(0);
    }

    private void getFeignService(Class<?> resourceClass) {
//        switch (resourceClass.) {
//
//        }
    }

    public static void main(String[] args) {

    }
}
