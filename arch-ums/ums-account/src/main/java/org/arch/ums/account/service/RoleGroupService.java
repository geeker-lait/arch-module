package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleGroupDao;
import org.arch.ums.account.entity.RoleGroup;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 获取所有租户的所有角色组的角色
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Set<String>>> listAllGroups() {
        // TODO: 2021.3.26
        return new HashMap<>(0);
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
        // TODO: 2021.3.26
        return new HashMap<>(0);
    }
}
