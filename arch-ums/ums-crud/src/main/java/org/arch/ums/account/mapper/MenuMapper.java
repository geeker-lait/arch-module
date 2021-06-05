package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Menu;

/**
 * 账号-菜单(Menu) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:15:46
 * @since 1.0.0
 */
@Mapper
public interface MenuMapper extends CrudMapper<Menu> {

}