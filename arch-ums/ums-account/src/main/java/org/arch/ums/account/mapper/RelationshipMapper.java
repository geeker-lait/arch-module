package org.arch.ums.account.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.Relationship;

/**
 * 账号-关系(Relationship) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:36:56
 * @since 1.0.0
 */
@Mapper
public interface RelationshipMapper extends CrudMapper<Relationship> {

}
