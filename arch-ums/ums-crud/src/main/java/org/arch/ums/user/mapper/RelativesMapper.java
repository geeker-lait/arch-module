package org.arch.ums.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.user.entity.Relatives;

/**
 * 用户亲朋信息(Relatives) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:02:25
 * @since 1.0.0
 */
@Mapper
public interface RelativesMapper extends CrudMapper<Relatives> {

}