package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.RoleMenu;
import org.arch.ums.account.mapper.RoleMenuMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色菜单(RoleMenu) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:26:45
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RoleMenuDao extends CrudServiceImpl<RoleMenuMapper, RoleMenu> implements CrudDao<RoleMenu> {
    private final RoleMenuMapper roleMenuMapper;
}