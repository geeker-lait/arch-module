package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.mapper.MenuMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-菜单(Menu) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:14:41
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class MenuDao extends CrudServiceImpl<MenuMapper, Menu> implements CrudDao<Menu> {
    private final MenuMapper menuMapper;
}