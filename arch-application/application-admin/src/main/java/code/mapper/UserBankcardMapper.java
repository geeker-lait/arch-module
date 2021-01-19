package code.mapper;

import code.entity.UserBankcard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-银行卡信息(user_bankcard)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface UserBankcardMapper extends BaseMapper<UserBankcard> {

}
