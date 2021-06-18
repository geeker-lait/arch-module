package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.mapper.PermissionMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-权限(Permission) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:17:36
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class PermissionDao extends CrudServiceImpl<PermissionMapper, Permission> implements CrudDao<Permission> {
    private final PermissionMapper permissionMapper;
}