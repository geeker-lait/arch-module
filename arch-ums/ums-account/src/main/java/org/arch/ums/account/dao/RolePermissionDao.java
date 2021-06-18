package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.mapper.RolePermissionMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色权限表(RolePermission) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:28:35
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RolePermissionDao extends CrudServiceImpl<RolePermissionMapper, RolePermission> implements CrudDao<RolePermission> {
    private final RolePermissionMapper rolePermissionMapper;
}