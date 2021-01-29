package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
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
@Repository
public class RoleMenuDao extends ServiceImpl<RoleMenuMapper, RoleMenu> implements CrudDao<RoleMenu> {

}