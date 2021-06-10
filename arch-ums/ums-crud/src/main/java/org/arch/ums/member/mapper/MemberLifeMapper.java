package org.arch.ums.member.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.member.entity.MemberLife;

/**
 * 会员生命周期(MemberLife) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:19
 * @since 1.0.0
 */
@Mapper
public interface MemberLifeMapper extends CrudMapper<MemberLife> {

}
