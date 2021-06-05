package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.RoleMenu;

/**
 * 账号-角色菜单(RoleMenu) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:26:52
 * @since 1.0.0
 */
@Mapper
public interface RoleMenuMapper extends CrudMapper<RoleMenu> {

}