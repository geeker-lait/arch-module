package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.account.mapper.RoleResourceMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色资源表(RoleResource) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:29:37
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RoleResourceDao extends CrudServiceImpl<RoleResourceMapper, RoleResource> implements CrudDao<RoleResource> {
    private final RoleResourceMapper roleResourceMapper;
}