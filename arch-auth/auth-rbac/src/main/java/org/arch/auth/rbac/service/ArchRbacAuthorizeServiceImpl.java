package org.arch.auth.rbac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.api.premission.service.AbstractUriAuthorizeService;
import top.dcenter.ums.security.core.api.premission.service.UpdateAndCacheRolesResourcesService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * RBAC 权限服务
 * @see AbstractUriAuthorizeService
 * @author YongWu zheng
 * @since 2020.12.29 20:21
 */
@RequiredArgsConstructor
public class ArchRbacAuthorizeServiceImpl extends AbstractUriAuthorizeService implements UpdateAndCacheRolesResourcesService, InitializingBean {

    /**
     * 所有角色 uri(资源) 权限 Map(role, map(uri, Set(permission)))
     */
    private volatile Map<String, Map<String, Set<String>>> rolesAuthoritiesMap;

    private final Object lock = new Object();

    @Override
    public void updateAuthoritiesOfAllRoles() {

        synchronized (lock) {
            // 更新并缓存所有角色 uri(资源) 权限 Map<role, Map<uri, Set<permission>>>
            this.rolesAuthoritiesMap = updateRolesAuthorities();
        }

    }

    @Override
    public void afterPropertiesSet() {
        // 更新并缓存所有角色 uri(资源) 权限 Map<role, Map<uri, Set<permission>>>
        updateAuthoritiesOfAllRoles();

    }

    /**
     * 获取角色的 uri 的权限 map.<br>
     *     返回值为: Map(role, Map(uri, UriResourcesDTO))
     * @return Map(String, Map(String, String)) 的 key 为必须包含"ROLE_"前缀的角色名称(如: ROLE_ADMIN), value 为 UriResourcesDTO map
     * (key 为 uri, 此 uri 可以为 antPath 通配符路径,如 /user/**; value 为 UriResourcesDTO).
     */
    @Override
    @NonNull
    public Map<String, Map<String, Set<String>>> getRolesAuthorities() {

        if (this.rolesAuthoritiesMap != null) {
            return this.rolesAuthoritiesMap;
        }
        else {
            return updateRolesAuthorities();
        }

    }

    /**
     * 更新并缓存所有角色 uri(资源) 权限 Map<role, Map<uri, Set<permission>>>.<br>
     * @return 所有角色 uri(资源) 权限 Map<role, Map<uri, Set<permission>>>
     */
    @NonNull
    private Map<String, Map<String, Set<String>>> updateRolesAuthorities() {

        // 从数据源获取所有角色的权限
        final Map<String, Map<String, Set<String>>> rolesAuthoritiesMap = new HashMap<>(3);

        // 修改 ums-core 实现
//        rolesAuthoritiesMap.putAll(rbacMenuService.getRolesAuthorities());
//        rolesAuthoritiesMap.putAll(rbacPermissionService.getRolesAuthorities());
//        rolesAuthoritiesMap.putAll(rbacResourceService.getRolesAuthorities());

        this.rolesAuthoritiesMap = rolesAuthoritiesMap;

        return rolesAuthoritiesMap;
    }
}
