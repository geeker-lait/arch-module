package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.account.mapper.RoleGroupMapper;
import org.springframework.stereotype.Repository;

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
}