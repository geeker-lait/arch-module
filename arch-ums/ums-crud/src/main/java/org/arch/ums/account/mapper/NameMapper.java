package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Name;

/**
 * 账号名(Name) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:54:15
 * @since 1.0.0
 */
@Mapper
public interface NameMapper extends CrudMapper<Name> {

}