package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.UserBankcard;
import code.mapper.UserBankcardMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户-银行卡信息(user_bankcard)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class UserBankcardDao extends ServiceImpl<UserBankcardMapper, UserBankcard> {

}