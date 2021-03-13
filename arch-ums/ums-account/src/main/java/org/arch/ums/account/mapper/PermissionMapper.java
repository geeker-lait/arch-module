package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Permission;

/**
 * 账号-权限(Permission) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:18:16
 * @since 1.0.0
 */
@Mapper
public interface PermissionMapper extends CrudMapper<Permission> {

}