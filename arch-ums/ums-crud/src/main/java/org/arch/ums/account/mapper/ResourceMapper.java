package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Resource;

/**
 * 账号-资源(Resource) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:22:55
 * @since 1.0.0
 */
@Mapper
public interface ResourceMapper extends CrudMapper<Resource> {

}