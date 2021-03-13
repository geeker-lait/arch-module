package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.RoleResource;

/**
 * 账号-角色资源表(RoleResource) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:29:44
 * @since 1.0.0
 */
@Mapper
public interface RoleResourceMapper extends CrudMapper<RoleResource> {

}