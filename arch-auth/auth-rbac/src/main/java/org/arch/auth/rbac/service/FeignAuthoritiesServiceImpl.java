package org.arch.auth.rbac.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.feign.MenuFeignService;
import org.arch.auth.rbac.feign.PermissionFeignService;
import org.arch.auth.rbac.feign.ResourceFeignService;
import org.arch.auth.rbac.feign.RoleFeignService;
import org.arch.auth.rbac.feign.RoleGroupFeignService;
import org.arch.auth.rbac.feign.RoleMenuFeignService;
import org.arch.auth.rbac.feign.RolePermissionFeignService;
import org.arch.auth.rbac.feign.RoleResourceFeignService;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.entity.Role;
import org.arch.ums.account.entity.RoleMenu;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.account.vo.MenuVo;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
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
@Slf4j
@RequiredArgsConstructor
public class FeignAuthoritiesServiceImpl implements AuthoritiesService {

    private final RoleMenuFeignService roleMenuFeignService;
    private final RoleGroupFeignService roleGroupFeignService;
    private final RoleResourceFeignService roleResourceFeignService;
    private final RolePermissionFeignService rolePermissionFeignService;
    private final MenuFeignService menuFeignService;
    private final RoleFeignService roleFeignService;
    private final PermissionFeignService permissionFeignService;
    private final ResourceFeignService resourceFeignService;

    @NonNull
    @Override
    public Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllTenant() {

        // Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
        Map<String, Map<String, Map<String, Set<String>>>> roleResourceMap = listAllResourceAuthorities();
        Map<String, Map<String, Map<String, Set<String>>>> rolePermissionMap = listAllPermissionAuthorities();

        int size = rolePermissionMap.size() + roleResourceMap.size();
        final Map<String, Map<String, Map<String, Set<String>>>> allAuthorityMap = new HashMap<>(size);
        allAuthorityMap.putAll(rolePermissionMap);
        allAuthorityMap.putAll(roleResourceMap);
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
        return ofNullable(this.roleGroupFeignService.listAllGroups().getSuccessData())
                .orElse(new HashMap<>(0));
    }

    @NonNull
    @Override
    public Map<String, Map<String, Set<String>>> getGroupRolesByGroupIdOfTenant(@NonNull Integer tenantId,
                                                                                @NonNull Long groupId,
                                                                                Long... roleIds) {
        try {
            // Map(tenantAuthority, Map (groupAuthority, Set(roleAuthority)))
            return ofNullable(this.roleGroupFeignService.findGroupRolesByGroupIdOfTenant(tenantId,
                                                                                         groupId,
                                                                                         asList(roleIds))
                                                        .getSuccessData())
                    .orElse(new HashMap<>(0));
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
        // Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
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
        CompletableFuture<List<Menu>> menuCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(menuFeignService.list()
                                                                               .getSuccessData())
                                                                    .orElse(new ArrayList<>(0)));

        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleFeignService.list()
                                                                               .getSuccessData())
                                                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<RoleMenu>> roleMenuCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleMenuFeignService.list()
                                                                                   .getSuccessData())
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
        CompletableFuture<List<Menu>> menuCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(menuFeignService.findByMenuIds(tenantId, menuIds)
                                                                               .getSuccessData())
                                                               .orElse(new ArrayList<>(0)));

        CompletableFuture<Role> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> roleFeignService.findById(roleId).getSuccessData());

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Map<MenuVo, Set<MenuVo>>> resultCompletableFuture =
                CompletableFuture.allOf(menuCompletableFuture, roleCompletableFuture)
                                 .thenApplyAsync(ignore -> groupingOfFindMenu(menuCompletableFuture, mdcTraceId, log));

        try {
            Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> result = new HashMap<>(1);
            Map<String, Map<MenuVo, Set<MenuVo>>> roleMenusMap = new HashMap<>(1);
            Map<MenuVo, Set<MenuVo>> menuVoSetMap = resultCompletableFuture.get();
            roleMenusMap.put(ROLE_PREFIX + roleCompletableFuture.get().getRoleName(), menuVoSetMap);
            result.put(TENANT_PREFIX + tenantId, roleMenusMap);
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
        CompletableFuture<List<Resource>> resourceCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(resourceFeignService.list()
                                                                                   .getSuccessData())
                        .orElse(new ArrayList<>(0)));

        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleFeignService.list()
                                                                               .getSuccessData())
                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<RoleResource>> roleResourceCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleResourceFeignService.list()
                                                                                       .getSuccessData())
                        .orElse(new ArrayList<>(0)));

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
        CompletableFuture<List<Permission>> permissionCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(permissionFeignService.list()
                                                                                   .getSuccessData())
                        .orElse(new ArrayList<>(0)));

        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleFeignService.list()
                                                                               .getSuccessData())
                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<RolePermission>> rolePermissionCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(rolePermissionFeignService.list()
                                                                                       .getSuccessData())
                        .orElse(new ArrayList<>(0)));

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
                                                       () -> ofNullable(resourceFeignService
                                                                                .findByResourceIds(tenantId,
                                                                                                   asList(resourceIds))
                                                                                .getSuccessData())
                                                               .orElse(new ArrayList<>(0)),
                                                       () -> roleFeignService.findById(roleId).getSuccessData(),
                                                       toMap(Resource::getResourcePath,
                                                             resource -> string2Set(resource.getResourceVal(),
                                                                                    AUTHORITY_SEPARATOR),
                                                             (oldVal, newVal) -> {
                                                                 oldVal.addAll(newVal);
                                                                 return oldVal;
                                                             }),
                                                       String.format("获取角色资源权限信息失败: tenantId=%s, roleId=%s",
                                                                     tenantId, roleId),
                                                       log);
            case "org.arch.ums.account.entity.Permission":
                return findAuthoritiesByRoleIdOfTenant(tenantId,
                                                       roleId,
                                                       () -> ofNullable(permissionFeignService
                                                                                .findByPermissionIds(tenantId,
                                                                                                     asList(resourceIds))
                                                                                .getSuccessData())
                                                               .orElse(new ArrayList<>(0)),
                                                       () -> roleFeignService.findById(roleId).getSuccessData(),
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
