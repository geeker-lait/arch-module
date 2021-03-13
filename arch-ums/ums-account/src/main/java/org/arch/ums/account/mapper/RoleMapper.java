package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Role;

/**
 * 账号-角色(Role) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:23:45
 * @since 1.0.0
 */
@Mapper
public interface RoleMapper extends CrudMapper<Role> {

}