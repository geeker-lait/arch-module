package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.account.mapper.RoleGroupMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;

/**
 * 账号-角色组织或机构(RoleGroup) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:25:24
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RoleGroupDao extends CrudServiceImpl<RoleGroupMapper, RoleGroup> implements CrudDao<RoleGroup> {
    private final RoleGroupMapper roleGroupMapper;

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
        return ofNullable(roleGroupMapper.findRolesByGroupIdOfTenant(tenantId, groupId))
                .orElse(new HashSet<>(0));
    }
}