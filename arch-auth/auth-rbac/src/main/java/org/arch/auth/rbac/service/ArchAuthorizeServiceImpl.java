/*
 * MIT License
 * Copyright (c) 2020-2029 YongWu zheng (dcenter.top and gitee.com/pcore and github.com/ZeroOrInfinity)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.arch.auth.rbac.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.api.premission.service.AbstractUriAuthorizeService;
import top.dcenter.ums.security.core.api.premission.service.UpdateAndCacheRolesResourcesService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author YongWu zheng
 * @since 2020.12.29 20:21
 */
public class ArchAuthorizeServiceImpl extends AbstractUriAuthorizeService implements UpdateAndCacheRolesResourcesService, InitializingBean {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private RbacMenuService rbacMenuService;
    private RbacResourceService rbacResourceService;
    private RbacPermissionService rbacPermissionService;

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
        final Map<String, Map<String, Set<String>>> rolesAuthoritiesMap = new HashMap<>();

        // 修改 ums-core 实现
//        rolesAuthoritiesMap.putAll(rbacMenuService.getRolesAuthorities());
//        rolesAuthoritiesMap.putAll(rbacPermissionService.getRolesAuthorities());
//        rolesAuthoritiesMap.putAll(rbacResourceService.getRolesAuthorities());

        this.rolesAuthoritiesMap = rolesAuthoritiesMap;

        return rolesAuthoritiesMap;
    }
}