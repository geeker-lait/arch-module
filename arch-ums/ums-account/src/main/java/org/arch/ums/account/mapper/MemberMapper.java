package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.Member;

/**
 * 账号-会员账号(Member) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:12:46
 * @since 1.0.0
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}