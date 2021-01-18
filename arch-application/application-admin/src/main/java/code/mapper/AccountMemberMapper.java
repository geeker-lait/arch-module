package code.mapper;

import code.entity.AccountMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-会员账号(account_member)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface AccountMemberMapper extends BaseMapper<AccountMember> {

}
