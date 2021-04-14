package org.arch.ums.rbac.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.service.AuthoritiesService;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.entity.Role;
import org.arch.ums.account.entity.RoleMenu;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.account.service.MenuService;
import org.arch.ums.account.service.PermissionService;
import org.arch.ums.account.service.ResourceService;
import org.arch.ums.account.service.RoleGroupService;
import org.arch.ums.account.service.RoleMenuService;
import org.arch.ums.account.service.RolePermissionService;
import org.arch.ums.account.service.RoleResourceService;
import org.arch.ums.account.service.RoleService;
import org.arch.ums.account.vo.MenuVo;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static org.arch.framework.ums.consts.MdcConstants.MDC_KEY;
import static org.arch.framework.ums.consts.RoleConstants.AUTHORITY_SEPARATOR;
import static org.arch.framework.ums.consts.RoleConstants.ROLE_PREFIX;
import static org.arch.framework.ums.consts.RoleConstants.TENANT_PREFIX;
import static top.dcenter.ums.security.core.util.ConvertUtil.string2Set;

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

    private final RoleMenuService roleMenuService;
    private final RoleGroupService roleGroupService;
    private final RoleResourceService roleResourceService;
    private final RolePermissionService rolePermissionService;
    private final MenuService menuService;
    private final RoleService roleService;
    private final ResourceService resourceService;
    private final PermissionService permissionService;


    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllTenant() {

        Map<String, Map<String, Map<String, Set<String>>>> roleResourceMap = listAllResourceAuthorities();
        Map<String, Map<String, Map<String, Set<String>>>> rolePermissionMap = listAllPermissionAuthorities();

        int size = rolePermissionMap.size() + roleResourceMap.size();
        // Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
        final Map<String, Map<String, Map<String, Set<String>>>> allAuthorityMap = new HashMap<>(size);
        allAuthorityMap.putAll(roleResourceMap);
        allAuthorityMap.putAll(rolePermissionMap);
        return allAuthorityMap;
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllScopes() {
        // Map(scopeAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
        // TODO: 实现 auth-oauth 模块后再实现
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getAllGroupRolesOfAllTenant() {
        // Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
        return this.roleGroupService.listAllGroups();
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getGroupRolesByGroupIdOfTenant(@NonNull Integer tenantId,
                                                                                @NonNull Long groupId,
                                                                                Long... roleIds) {
        try {
            // Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
            return this.roleGroupService.findGroupRolesByGroupIdOfTenant(tenantId,
                                                                         groupId,
                                                                         asList(roleIds));
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
    public Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                                                              @NonNull Long roleId,
                                                                                              @NonNull Class<?> resourceClass,
                                                                                              Long... resourceIds) throws RolePermissionsException {
        // Map(scopeAuthority, Map ( roleAuthority, map ( uri / path, Set ( permission)))
        // TODO: 实现 auth-oauth 模块后再实现
        return new HashMap<>(0);
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> getMenuByRoleOfTenant(@NonNull Integer tenantId,
                                                                                 @NonNull Long roleId,
                                                                                 Long... menuIds) {

        // Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], List(Menu[sorted])))), 中括号中的排序字段.
        return findMenuByRoleOfTenant(tenantId, roleId, asList(menuIds));
    }

    @NonNull
    @Override
    public Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> getAllMenuOfAllTenant() {
        // Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], List(Menu[sorted])))), 中括号中的排序字段.
        return listAllMenuOfAllTenant();
    }

    /**
     * 获取所有租户的所有角色的菜单权限
     * @return 所拥有的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @NonNull
    private Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> listAllMenuOfAllTenant() {
        //@formatter:off
        Wrapper<Menu> menuWrapper = Wrappers.<Menu>lambdaQuery()
                                        .eq(Menu::getDeleted, Boolean.FALSE);
        Wrapper<Role> roleWrapper = Wrappers.<Role>lambdaQuery()
                                        .eq(Role::getDeleted, Boolean.FALSE);
        Wrapper<RoleMenu> roleMenuWrapper = Wrappers.<RoleMenu>lambdaQuery()
                                        .eq(RoleMenu::getDeleted, Boolean.FALSE);
        CompletableFuture<List<Menu>> menuCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(menuService.findAllBySpec(menuWrapper))
                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleService.findAllBySpec(roleWrapper))
                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<RoleMenu>> roleMenuCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleMenuService.findAllBySpec(roleMenuWrapper))
                        .orElse(new ArrayList<>(0)));

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>>> resultCompletableFuture =
                CompletableFuture.allOf(menuCompletableFuture, roleCompletableFuture, roleMenuCompletableFuture)
                                 .thenApplyAsync(ignore -> groupingOfListAllMenu(menuCompletableFuture,
                                                                                 roleCompletableFuture,
                                                                                 roleMenuCompletableFuture,
                                                                                 mdcTraceId,
                                                                                 log));

        try {
            return resultCompletableFuture.get();
        }
        catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    /**
     * 多租户获取指定角色指定菜单的信息, 只包含带有 uri 与 permission(menu_vul) 的菜单权限
     *
     * @param tenantId  多租户 ID
     * @param roleId    用户的角色 Id
     * @param menuIds   用户的菜单 ids
     * @return 所拥有的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @NonNull
    private Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> findMenuByRoleOfTenant(
                                                                    @PathVariable(value = "tenantId") Integer tenantId,
                                                                    @PathVariable(value = "roleId") Long roleId,
                                                                    @RequestBody List<Long> menuIds) {
        //@formatter:off
        Wrapper<Menu> menuWrapper = Wrappers.lambdaQuery(Menu.class)
                                            .eq(Menu::getTenantId, tenantId)
                                            .in(Menu::getId, menuIds)
                                            .eq(Menu::getDeleted, Boolean.FALSE);

        CompletableFuture<List<Menu>> menuCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(menuService.findAllBySpec(menuWrapper))
                        .orElse(new ArrayList<>(0)));

        CompletableFuture<Role> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> roleService.findById(roleId));

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Map<MenuVo, Set<MenuVo>>> resultCompletableFuture =
                CompletableFuture.allOf(menuCompletableFuture, roleCompletableFuture)
                                 .thenApplyAsync(ignore -> groupingOfFindMenu(menuCompletableFuture, mdcTraceId, log));

        try {
            Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> result = new HashMap<>(1);
            Map<String, Map<MenuVo, Set<MenuVo>>> roleMenusMap = new HashMap<>(1);
            Map<MenuVo, Set<MenuVo>> menuVoSetMap = resultCompletableFuture.get();
            roleMenusMap.put(ROLE_PREFIX + roleCompletableFuture.get().getRoleName(), menuVoSetMap);
            result.put(TENANT_PREFIX + tenantId,roleMenusMap);
            return result;
        }
        catch (InterruptedException | ExecutionException e) {
            String msg = String.format("获取角色菜单权限信息失败: tenantId=%s, roleId=%s", tenantId, roleId);
            log.error(msg, e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    @NonNull
    private Map<String, Map<String, Map<String, Set<String>>>> listAllResourceAuthorities() {
        //@formatter:off
        Wrapper<Resource> resourceWrapper = Wrappers.<Resource>lambdaQuery()
                .eq(Resource::getDeleted, Boolean.FALSE);
        Wrapper<Role> roleWrapper = Wrappers.<Role>lambdaQuery()
                .eq(Role::getDeleted, Boolean.FALSE);
        Wrapper<RoleResource> roleResourceWrapper = Wrappers.<RoleResource>lambdaQuery()
                .eq(RoleResource::getDeleted, Boolean.FALSE);
        CompletableFuture<List<Resource>> resourceCompletableFuture =
                CompletableFuture.supplyAsync(() -> resourceService.findAllBySpec(resourceWrapper));

        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> roleService.findAllBySpec(roleWrapper));
        CompletableFuture<List<RoleResource>> roleResourceCompletableFuture =
                CompletableFuture.supplyAsync(() -> roleResourceService.findAllBySpec(roleResourceWrapper));

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Map<String, Map<String, Map<String, Set<String>>>>> resultCompletableFuture =
                CompletableFuture.allOf(resourceCompletableFuture, roleCompletableFuture, roleResourceCompletableFuture)
                                 .thenApplyAsync(ignore -> groupingOfListAllResource(resourceCompletableFuture,
                                                                                     roleCompletableFuture,
                                                                                     roleResourceCompletableFuture,
                                                                                     mdcTraceId,
                                                                                     log));

        try {
            // Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
            return resultCompletableFuture.get();
        }
        catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    @NonNull
    private Map<String, Map<String, Map<String, Set<String>>>> listAllPermissionAuthorities() {
        //@formatter:off
        Wrapper<Permission> permissionWrapper = Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getDeleted, Boolean.FALSE);
        Wrapper<Role> roleWrapper = Wrappers.<Role>lambdaQuery()
                .eq(Role::getDeleted, Boolean.FALSE);
        Wrapper<RolePermission> rolePermissionWrapper = Wrappers.<RolePermission>lambdaQuery()
                .eq(RolePermission::getDeleted, Boolean.FALSE);
        CompletableFuture<List<Permission>> permissionCompletableFuture =
                CompletableFuture.supplyAsync(() -> permissionService.findAllBySpec(permissionWrapper));

        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> roleService.findAllBySpec(roleWrapper));
        CompletableFuture<List<RolePermission>> rolePermissionCompletableFuture =
                CompletableFuture.supplyAsync(() -> rolePermissionService.findAllBySpec(rolePermissionWrapper));

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Map<String, Map<String, Map<String, Set<String>>>>> resultCompletableFuture =
                CompletableFuture.allOf(permissionCompletableFuture, roleCompletableFuture, rolePermissionCompletableFuture)
                                 .thenApplyAsync(ignore -> groupingOfListAllPermission(permissionCompletableFuture,
                                                                                       roleCompletableFuture,
                                                                                       rolePermissionCompletableFuture,
                                                                                       mdcTraceId,
                                                                                       log));

        try {
            // Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
            return resultCompletableFuture.get();
        }
        catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    @Override
    @NonNull
    public Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByResourceClass(@NonNull Integer tenantId,
                                                                                     @NonNull Long roleId,
                                                                                     @NonNull Class<?> resourceClass,
                                                                                     Long... resourceIds) {
        // Map(tenantAuthority, Map ( roleAuthority, map ( uri / path, Set ( permission)))

        switch (resourceClass.getName()) {
            case "org.arch.ums.account.entity.Resource":
                return findAuthoritiesByRoleIdOfTenant(tenantId,
                                                       roleId,
                                                       () -> {
                                                           Wrapper<Resource> resourceWrapper =
                                                                   Wrappers.lambdaQuery(Resource.class)
                                                                           .select(Resource::getId,
                                                                                   Resource::getResourceCode,
                                                                                   Resource::getResourcePath,
                                                                                   Resource::getResourceVal)
                                                                           .eq(Resource::getTenantId, tenantId)
                                                                           .in(Resource::getId, asList(resourceIds))
                                                                           .eq(Resource::getDeleted, Boolean.FALSE);
                                                           return resourceService.findAllBySpec(resourceWrapper);
                                                       },
                                                       () -> roleService.findById(roleId),
                                                       toMap(Resource::getResourcePath,
                                                             resource -> string2Set(resource.getResourceVal(),
                                                                                    AUTHORITY_SEPARATOR),
                                                             (oldVal, newVal) -> {
                                                                 oldVal.addAll(newVal);
                                                                 return oldVal;
                                                             }),
                                                       String.format("获取角色资源信息失败: tenantId=%s, roleId=%s",
                                                                     tenantId, roleId),
                                                       log);
            case "org.arch.ums.account.entity.Permission":
                return findAuthoritiesByRoleIdOfTenant(tenantId,
                                                       roleId,
                                                       () -> {
                                                           Wrapper<Permission> permissionWrapper =
                                                                   Wrappers.lambdaQuery(Permission.class)
                                                                           .select(Permission::getId,
                                                                                   Permission::getPermissionCode,
                                                                                   Permission::getPermissionUri,
                                                                                   Permission::getPermissionVal)
                                                                           .eq(Permission::getTenantId, tenantId)
                                                                           .in(Permission::getId, asList(resourceIds))
                                                                           .eq(Permission::getDeleted, Boolean.FALSE);
                                                           return permissionService.findAllBySpec(permissionWrapper);
                                                       },
                                                       () -> roleService.findById(roleId),
                                                       toMap(Permission::getPermissionUri,
                                                             resource -> string2Set(resource.getPermissionVal(),
                                                                                    AUTHORITY_SEPARATOR),
                                                             (oldVal, newVal) -> {
                                                                 oldVal.addAll(newVal);
                                                                 return oldVal;
                                                             }),
                                                       String.format("获取角色权限信息失败: tenantId=%s, roleId=%s",
                                                                     tenantId, roleId),
                                                       log);
            default:
                throw new RolePermissionsException(ErrorCodeEnum.PARAMETER_ERROR, "resourceClass");
        }

    }

}
