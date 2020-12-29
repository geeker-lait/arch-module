package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.entity.UserBankCard;
import org.arch.ums.mapper.UserBankCardMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户银行卡信息(user_bank_card)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserBankCardDao extends ServiceImpl<UserBankCardMapper, UserBankCard> {

}