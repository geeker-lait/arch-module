package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleGroupDao;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.entity.Role;
import org.arch.ums.account.entity.RoleGroup;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.arch.framework.ums.consts.MdcConstants.MDC_KEY;
import static org.arch.framework.ums.consts.RoleConstants.GROUP_PREFIX;
import static org.arch.framework.ums.consts.RoleConstants.ROLE_PREFIX;
import static org.arch.framework.ums.consts.RoleConstants.TENANT_PREFIX;

/**
 * 账号-角色组织或机构(RoleGroup) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleGroupService extends CrudService<RoleGroup, java.lang.Long> {
    private final RoleGroupDao roleGroupDao;
    private final RoleService roleService;
    private final GroupService groupService;

    /**
     * 获取所有租户的所有角色组的角色
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Set<String>>> listAllGroups() {

        //@formatter:off
        Wrapper<Group> groupWrapper = Wrappers.<Group>lambdaQuery()
                                              .eq(Group::getDeleted,FALSE);
        Wrapper<Role> roleWrapper = Wrappers.<Role>lambdaQuery()
                                            .eq(Role::getDeleted,FALSE);
        Wrapper<RoleGroup> roleGroupWrapper = Wrappers.<RoleGroup>lambdaQuery()
                                                      .eq(RoleGroup::getDeleted,FALSE);
        CompletableFuture<List<Group>> groupCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(groupService.findAllBySpec(groupWrapper))
                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleService.findAllBySpec(roleWrapper))
                        .orElse(new ArrayList<>(0)));
        CompletableFuture<List<RoleGroup>> roleGroupCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleGroupDao.list(roleGroupWrapper))
                        .orElse(new ArrayList<>(0)));

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Map<String, Map<String, Set<String>>>> resultCompletableFuture =
                CompletableFuture.allOf(groupCompletableFuture, roleCompletableFuture, roleGroupCompletableFuture)
                                 .thenApplyAsync(ignore -> groupingOfListAllGroup(groupCompletableFuture,
                                                                                  roleCompletableFuture,
                                                                                  roleGroupCompletableFuture,
                                                                                  mdcTraceId));

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
     * 多租户获取 所有 group 的所有角色资源
     *
     * @param tenantId 多租户 ID
     * @param groupId  用户的 groupId
     * @param roleIds  用户的角色 ids
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Set<String>>> findGroupRolesByGroupIdOfTenant(@NonNull Integer tenantId,
                                                                                 @NonNull Long groupId,
                                                                                 @NonNull List<Long> roleIds) {
        //@formatter:off
        Wrapper<Role> roleWrapper = Wrappers.lambdaQuery(Role.class)
                                            .eq(Role::getTenantId, tenantId)
                                            .in(Role::getId, roleIds)
                                            .eq(Role::getDeleted, FALSE);
        CompletableFuture<List<Role>> roleCompletableFuture =
                CompletableFuture.supplyAsync(() -> ofNullable(roleService.findAllBySpec(roleWrapper))
                        .orElse(new ArrayList<>(0)));

        CompletableFuture<Group> groupCompletableFuture =
                CompletableFuture.supplyAsync(() -> groupService.findById(groupId));

        final String mdcTraceId = MDC.get(MDC_KEY);
        CompletableFuture<Set<String>> roleAuthorityCompletableFuture =
                CompletableFuture.allOf(roleCompletableFuture, groupCompletableFuture)
                                 .thenApplyAsync(ignore -> {
                                     try {
                                         return roleCompletableFuture.get().stream()
                                                              .map(role -> ROLE_PREFIX + role.getRoleName())
                                                              .collect(toSet());
                                     }
                                     catch (InterruptedException | ExecutionException e) {
                                         MDC.put(MDC_KEY, mdcTraceId);
                                         log.error(e.getMessage(), e);
                                         return new HashSet<>(0);
                                     }
                                 });

        try {
            // Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
            Map<String, Map<String, Set<String>>> result = new HashMap<>(1);
            Map<String, Set<String>> groupRolesMap = new HashMap<>(1);
            Group group = groupCompletableFuture.get();
            final String groupAuthority = GROUP_PREFIX + group.getGroupName();
            groupRolesMap.put(groupAuthority, roleAuthorityCompletableFuture.get());
            result.put(TENANT_PREFIX + tenantId, groupRolesMap);
            return result;
        }
        catch (InterruptedException | ExecutionException e) {
            String msg = String.format("获取角色组权限信息失败: tenantId=%s, groupId=%s", tenantId, groupId);
            log.error(msg, e);
            return new HashMap<>(0);
        }
        //@formatter:on
    }

    /**
     * 基于多租户, 查询指定角色组 {@code groupId} 所拥有的所有角色集合, Set(roleAuthority).
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @return groupId 所拥有的所有角色集合, Set(roleAuthority).
     */
    @Transactional(readOnly = true)
    @NonNull
    public Set<String> findRolesByGroupIdOfTenant(@NonNull Long tenantId, @NonNull Long groupId) {
        try {
            return roleGroupDao.findRolesByGroupIdOfTenant(tenantId, groupId);
        }
        catch (Exception e) {
            String msg = String.format("获取角色组角色集合信息失败: tenantId=%s, groupId=%s", tenantId, groupId);
            log.error(msg, e);
            return new HashSet<>(0);
        }
    }

    /**
     * 基于多租户, 更新角色组 {@code groupId} 的角色集合
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @param roleIds  角色 ids
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public boolean updateRolesByGroupIdOfTenant(@NonNull Long tenantId, @NonNull Long groupId,
                                                @NonNull List<Long> roleIds) {
        List<RoleGroup> roleGroupList =
                roleIds.stream()
                       .map(roleId -> new RoleGroup().setGroupId(groupId)
                                                     .setRoleId(roleId)
                                                     .setTenantId(tenantId.intValue())
                                                     .setDeleted(FALSE))
                       .collect(toList());

        Wrapper<RoleGroup> roleGroupWrapper = Wrappers.lambdaQuery(RoleGroup.class)
                                                      .eq(RoleGroup::getTenantId, tenantId)
                                                      .eq(RoleGroup::getGroupId, groupId)
                                                      .eq(RoleGroup::getDeleted, FALSE);
        boolean removeResult = roleGroupDao.remove(roleGroupWrapper);
        if (!removeResult) {
            return false;
        }
        return roleGroupDao.saveBatch(roleGroupList);
    }

    private Map<String, Map<String, Set<String>>> groupingOfListAllGroup(CompletableFuture<List<Group>> groupCompletableFuture,
                                                                         CompletableFuture<List<Role>> roleCompletableFuture,
                                                                         CompletableFuture<List<RoleGroup>> roleGroupCompletableFuture,
                                                                         String mdcTraceId) {
        //@formatter:off
        try {
            // RoleId 与 GroupId 全局主键唯一, 忽略根据多租户分组
            final Map<Long, Role> roleMap =
                    roleCompletableFuture.get().stream()
                                         .collect(toMap(Role::getId, role -> role));
            final Map<Long, Group> groupMap =
                    groupCompletableFuture.get().stream()
                                          .collect(toMap(Group::getId, group -> group));

            // Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority)))
            //noinspection UnnecessaryLocalVariable
            Map<String, Map<String, Set<String>>> result =
                roleGroupCompletableFuture.get().stream()
                      .collect(groupingBy(roleGroup -> TENANT_PREFIX + roleGroup.getTenantId(),
                                          groupingBy(roleGroup -> GROUP_PREFIX + groupMap.get(roleGroup.getGroupId())
                                                                                         .getGroupName(),
                                                     mapping(roleGroup1 ->
                                                                     ROLE_PREFIX + roleMap.get(roleGroup1.getRoleId())
                                                                                          .getRoleName(),
                                                             toSet())
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

}
