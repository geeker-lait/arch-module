package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-会员账号(account_member)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountMemberMapper extends BaseMapper<AccountMember> {

}
