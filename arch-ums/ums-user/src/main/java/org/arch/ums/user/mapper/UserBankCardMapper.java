package org.arch.ums.mapper;

import org.arch.ums.entity.UserBankCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户银行卡信息(user_bank_card)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface UserBankCardMapper extends BaseMapper<UserBankCard> {

}
