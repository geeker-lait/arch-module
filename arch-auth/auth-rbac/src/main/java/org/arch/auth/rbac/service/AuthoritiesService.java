package org.arch.auth.rbac.service;

import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.dto.RoleMenuSearchDto;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.arch.ums.account.dto.RoleResourceSearchDto;
import org.arch.ums.account.dto.RoleSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.vo.MenuVo;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.arch.framework.ums.consts.MdcConstants.MDC_KEY;
import static org.arch.framework.ums.consts.RoleConstants.AUTHORITY_SEPARATOR;
import static org.arch.framework.ums.consts.RoleConstants.ROLE_PREFIX;
import static org.arch.framework.ums.consts.RoleConstants.TENANT_PREFIX;
import static top.dcenter.ums.security.core.util.ConvertUtil.string2Set;

/**
 * 获取基于 RBAC 的所有权限资源服务
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 22:17
 */
public interface AuthoritiesService {

    /**
     * 获取 所有多租户 的所有角色的权限资源
     * @return  Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
     */
    @NonNull
    Map<String, Map<String, Map<String, Set<String>>>> getAllAuthoritiesOfAllTenant();

    /**
     * 获取 所有 scopes 的所有角色的权限资源
     * @return  Map(scopeAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
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
     * @return Map(tenantAuthority, Map ( roleAuthority, map ( uri / path, Set ( permission)))
     * @throws RolePermissionsException 获取缓存角色资源信息失败
     */
    @NonNull
    default Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByRoleIdOfTenant(@NonNull Integer tenantId,
                                                              @NonNull Long roleId,
                                                              @NonNull Class<?> resourceClass,
                                                              Long... resourceIds) throws RolePermissionsException {
        try {
            // Map(tenantAuthority, Map ( roleAuthority, map ( uri / path, Set ( permission)))
            return getAuthoritiesByResourceClass(tenantId, roleId, resourceClass, resourceIds);
        }
        catch (Exception e) {
            String msg = String.format("获取角色权限信息失败: tenantId=%s, roleId=%s, resourceIds=%s",
                                       tenantId, roleId, Arrays.toString(resourceIds));
            throw new RolePermissionsException(ErrorCodeEnum.QUERY_ROLE_PERMISSIONS_FAILURE, msg, e);
        }
    }

    /**
     * 获取 scopeId 的角色(roleId)所拥有的资源信息缓存.
     *
     * @param scopeId       scope id
     * @param roleId        角色 Id
     * @param resourceClass 资源 class
     * @param resourceIds   资源 Ids
     * @return Map(scopeAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
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

    /**
     * 多租户获取指定角色指定权限/资源的信息
     *
     * @param tenantId                  多租户 ID
     * @param roleId                    角色 ID
     * @param permissionSupplier        获取 {@code P} 类型列表的 {@link Supplier}
     * @param roleSupplier              获取 {@link RoleSearchDto} 的 {@link Supplier}
     * @param permissionMapCollector    转换为 {@code map (uri/path, Set(permission))} 的 {@link Collector}
     * @param errorMsg                  错误提示信息
     * @param log                       log
     * @param <P>   {@link java.security.Permissions} or {@link ResourceSearchDto}
     * @return Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission))), 如果不存在这返回空集合.
     */
    @NonNull
    default <P> Map<String, Map<String, Map<String, Set<String>>>> findAuthoritiesByRoleIdOfTenant(
                                            @NonNull Integer tenantId,
                                            @NonNull Long roleId,
                                            @NonNull Supplier<List<P>> permissionSupplier,
                                            @NonNull Supplier<RoleSearchDto> roleSupplier,
                                            @NonNull Collector<P, ?, Map<String, Set<String>>> permissionMapCollector,
                                            @NonNull String errorMsg,
                                            @NonNull Logger log) {

        //@formatter:off
        CompletableFuture<List<P>> permissionCompletableFuture =
                CompletableFuture.supplyAsync(permissionSupplier);

        CompletableFuture<RoleSearchDto> roleCompletableFuture =
                CompletableFuture.supplyAsync(roleSupplier);

        final String mdcTraceId = MDC.get(MDC_KEY);
        // map(uri/path, Set(permission))
        CompletableFuture<Map<String, Set<String>>> resultCompletableFuture =
                CompletableFuture.allOf(permissionCompletableFuture, roleCompletableFuture)
                                 .thenApplyAsync(ignore -> {
                                     try {
                                         return permissionCompletableFuture.get().stream()
                                                                           .collect(permissionMapCollector);
                                     }
                                     catch (InterruptedException | ExecutionException e) {
                                         MDC.put(MDC_KEY, mdcTraceId);
                                         log.error(e.getMessage(), e);
                                         return new HashMap<>(0);
                                     }
                                 });

        try {
            Map<String, Map<String, Map<String, Set<String>>>> result = new HashMap<>(1);
            Map<String, Map<String, Set<String>>> rolePermissionsMap = new HashMap<>(1);
            Map<String, Set<String>> resourceSetMap = resultCompletableFuture.get();
            rolePermissionsMap.put(ROLE_PREFIX + roleCompletableFuture.get().getRoleName(), resourceSetMap);
            result.put(TENANT_PREFIX + tenantId, rolePermissionsMap);
            return result;
        }
        catch (NullPointerException e){
            log.error(String.format("根据 %s 查询角色失败", roleId), e);
            return new HashMap<>(0);
        }
        catch (InterruptedException | ExecutionException e) {
            log.error(errorMsg, e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    /**
     * {@code findMenuByRoleOfTenant} 私有方法专用.
     * @param menuCompletableFuture menu CompletableFuture
     * @param mdcTraceId            mdc trace id
     * @param log                   log
     * @return  Map(Menu[level,sorted], Set(Menu[sorted])), 中括号中的排序字段.
     */
    default Map<MenuVo, Set<MenuVo>> groupingOfFindMenu(CompletableFuture<List<MenuSearchDto>> menuCompletableFuture,
                                                        String mdcTraceId, Logger log) {
        try {
            // MenuId 全局主键唯一, 忽略根据多租户分组
            final Map<Long, MenuVo> menuVoMap =
                    menuCompletableFuture.get().stream()
                                         .map(menu -> {
                                             MenuVo menuVo = new MenuVo();
                                             BeanUtils.copyProperties(menu, menuVo);
                                             return menuVo;
                                         })
                                         .collect(toMap(MenuVo::getId, menu -> menu));
            //noinspection UnnecessaryLocalVariable
            TreeMap<MenuVo, Set<MenuVo>> result =
                    menuVoMap.values().stream()
                             .collect(toMap(menuVo -> menuVo,
                                            pMenuVo -> {
                                                final Set<MenuVo> value = new TreeSet<>();
                                                menuVoMap.values().forEach(menuVo -> {
                                                    if (pMenuVo.getId().equals(menuVo.getPid())) {
                                                        value.add(menuVo);
                                                    }
                                                });
                                                return value;
                                            },
                                            (oldValue, newValue) -> {
                                                oldValue.addAll(newValue);
                                                return oldValue;
                                            },
                                            TreeMap::new));
            return result;
        } catch (CancellationException | InterruptedException | ExecutionException e) {
            MDC.put(MDC_KEY, mdcTraceId);
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
    }

    /**
     * 子类 {@code listAllMenuOfAllTenant} 私有方法专用.
     *
     * @param menuCompletableFuture     menu CompletableFuture
     * @param roleCompletableFuture     role CompletableFuture
     * @param roleMenuCompletableFuture roleMenu CompletableFuture
     * @param mdcTraceId                mdc trace id
     * @param log                       log
     * @return Map(tenantAuthority, Map ( roleAuthority, Map ( Menu[level, sorted], Set ( Menu[sorted])))), 中括号中的排序字段.
     */
    default Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> groupingOfListAllMenu(
                                            CompletableFuture<List<MenuSearchDto>> menuCompletableFuture,
                                            CompletableFuture<List<RoleSearchDto>> roleCompletableFuture,
                                            CompletableFuture<List<RoleMenuSearchDto>> roleMenuCompletableFuture,
                                            String mdcTraceId,
                                            Logger log) {

        //@formatter:off
        try {
            // RoleId 与 MenuId 全局主键唯一, 忽略根据多租户分组
            final Map<Long, RoleSearchDto> roleMap =
                    roleCompletableFuture.get().stream()
                                         .collect(toMap(RoleSearchDto::getId, role -> role));
            final Map<Long, MenuVo> menuVoMap =
                    menuCompletableFuture.get().stream()
                                         .map(menu -> {
                                             MenuVo menuVo = new MenuVo();
                                             BeanUtils.copyProperties(menu, menuVo);
                                             return menuVo;
                                         })
                                         .collect(toMap(MenuVo::getId, menu -> menu));
            //noinspection UnnecessaryLocalVariable
            Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> result =
                    roleMenuCompletableFuture.get().stream()
                         .collect(groupingBy(roleMenu -> TENANT_PREFIX + roleMenu.getTenantId(),
                                             groupingBy(roleMenu -> ROLE_PREFIX + roleMap.get(roleMenu.getRoleId())
                                                                                         .getRoleName(),
                                                        toMap(roleMenu -> menuVoMap.get(roleMenu.getMenuId()),
                                                              roleMenu -> {
                                                                  final Set<MenuVo> value = new TreeSet<>();
                                                                  MenuVo pMenuVo = menuVoMap.get(roleMenu.getMenuId());
                                                                  menuVoMap.values().forEach( menuVo -> {
                                                                      if (pMenuVo.getId().equals(menuVo.getPid())){
                                                                          value.add(menuVo);
                                                                      }
                                                                  });
                                                                  return value;
                                                              },
                                                              (oldValue, newValue) -> {
                                                                  oldValue.addAll(newValue);
                                                                  return oldValue;
                                                              },
                                                              TreeMap::new)
                                             )));
            return result;

        }
        catch (CancellationException | InterruptedException | ExecutionException e) {
            MDC.put(MDC_KEY, mdcTraceId);
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    /**
     * 子类 {@code listAllMenuOfAllTenant} 私有方法专用.
     *
     * @param resourceCompletableFuture     resource CompletableFuture
     * @param roleCompletableFuture         role CompletableFuture
     * @param roleResourceCompletableFuture roleResource CompletableFuture
     * @param mdcTraceId                    mdc trace id
     * @param log                           log
     * @return Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
     */
    default Map<String, Map<String, Map<String, Set<String>>>> groupingOfListAllResource(
                                        CompletableFuture<List<ResourceSearchDto>> resourceCompletableFuture,
                                        CompletableFuture<List<RoleSearchDto>> roleCompletableFuture,
                                        CompletableFuture<List<RoleResourceSearchDto>> roleResourceCompletableFuture,
                                        String mdcTraceId,
                                        Logger log) {

        //@formatter:off
        try {
            // RoleId 与 resourceId 全局主键唯一, 忽略根据多租户分组
            final Map<Long, RoleSearchDto> roleMap =
                    roleCompletableFuture.get().stream()
                                         .collect(toMap(RoleSearchDto::getId, role -> role));
            final Map<Long, ResourceSearchDto> resourceMap =
                    resourceCompletableFuture.get().stream()
                                         .collect(toMap(ResourceSearchDto::getId, resource -> resource));
            // Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
            //noinspection UnnecessaryLocalVariable
            Map<String, Map<String, Map<String, Set<String>>>> result =
                    roleResourceCompletableFuture.get().stream()
                         .collect(groupingBy(roleResource -> TENANT_PREFIX + roleResource.getTenantId(),
                                             groupingBy(roleResource -> ROLE_PREFIX + roleMap.get(roleResource
                                                                                                          .getRoleId())
                                                                                             .getRoleName(),
                                                        toMap(roleResource -> resourceMap.get(roleResource
                                                                                                      .getResourceId())
                                                                                         .getResourcePath(),
                                                              roleResource -> {
                                                                  ResourceSearchDto resource =
                                                                          resourceMap.get(roleResource.getResourceId());
                                                                  return string2Set(resource.getResourceVal(),
                                                                                    AUTHORITY_SEPARATOR);
                                                              },
                                                              (oldValue, newValue) -> {
                                                                  oldValue.addAll(newValue);
                                                                  return oldValue;
                                                              })
                                             )));
            return result;

        }
        catch (CancellationException | InterruptedException | ExecutionException e) {
            MDC.put(MDC_KEY, mdcTraceId);
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    /**
     * 子类 {@code listAllMenuOfAllTenant} 私有方法专用.
     *
     * @param permissionCompletableFuture       permission CompletableFuture
     * @param roleCompletableFuture             role CompletableFuture
     * @param rolePermissionCompletableFuture   rolePermission CompletableFuture
     * @param mdcTraceId                        mdc trace id
     * @param log                               log
     * @return Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
     */
    default Map<String, Map<String, Map<String, Set<String>>>> groupingOfListAllPermission(
                                    CompletableFuture<List<PermissionSearchDto>> permissionCompletableFuture,
                                    CompletableFuture<List<RoleSearchDto>> roleCompletableFuture,
                                    CompletableFuture<List<RolePermissionSearchDto>> rolePermissionCompletableFuture,
                                    String mdcTraceId,
                                    Logger log) {

        //@formatter:off
        try {
            // RoleId 与 permissionId 全局主键唯一, 忽略根据多租户分组
            final Map<Long, RoleSearchDto> roleMap =
                    roleCompletableFuture.get().stream()
                                         .collect(toMap(RoleSearchDto::getId, role -> role));
            final Map<Long, PermissionSearchDto> permissionMap =
                    permissionCompletableFuture.get().stream()
                                         .collect(toMap(PermissionSearchDto::getId, permission -> permission));
            // Map(tenantAuthority, Map(roleAuthority, map(uri/path, Set(permission)))
            //noinspection UnnecessaryLocalVariable
            Map<String, Map<String, Map<String, Set<String>>>> result =
                    rolePermissionCompletableFuture.get().stream()
                         .collect(groupingBy(rolePermission -> TENANT_PREFIX + rolePermission.getTenantId(),
                                             groupingBy(rolePermission -> ROLE_PREFIX + roleMap.get(rolePermission
                                                                                                          .getRoleId())
                                                                                             .getRoleName(),
                                                        toMap(rolePermission -> permissionMap.get(rolePermission
                                                                                                      .getPermissionId())
                                                                                         .getPermissionUri(),
                                                              rolePermission -> {
                                                                  PermissionSearchDto permission =
                                                                          permissionMap.get(rolePermission
                                                                                                    .getPermissionId());
                                                                  return string2Set(permission.getPermissionVal(),
                                                                                    AUTHORITY_SEPARATOR);
                                                              },
                                                              (oldValue, newValue) -> {
                                                                  oldValue.addAll(newValue);
                                                                  return oldValue;
                                                              })
                                             )));
            return result;

        }
        catch (CancellationException | InterruptedException | ExecutionException e) {
            MDC.put(MDC_KEY, mdcTraceId);
            log.error(e.getMessage(), e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    /**
     * {@link #getAuthoritiesByRoleIdOfTenant(Integer, Long, Class, Long...)} 专用方法.<br>
     * 获取多租户的角色(roleId)所拥有的资源或权限信息缓存
     * @param tenantId      多租户 ID
     * @param roleId        角色 ID
     * @param resourceClass 资源 class
     * @param resourceIds   资源/权限 Ids
     * @return Map(tenantAuthority, Map ( roleAuthority, map ( uri / path, Set ( permission)))
     */
    @NonNull
    Map<String, Map<String, Map<String, Set<String>>>> getAuthoritiesByResourceClass(@NonNull Integer tenantId,
                                                                                     @NonNull Long roleId,
                                                                                     @NonNull Class<?> resourceClass,
                                                                                     Long... resourceIds);
}
