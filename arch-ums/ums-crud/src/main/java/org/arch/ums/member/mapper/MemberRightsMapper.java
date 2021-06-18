package org.arch.ums.member.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.member.entity.MemberRights;

/**
 * 会员权益(MemberRights) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:22
 * @since 1.0.0
 */
@Mapper
public interface MemberRightsMapper extends CrudMapper<MemberRights> {

}
