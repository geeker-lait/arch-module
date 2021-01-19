package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.UserAccount;
import code.mapper.UserAccountMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户-账号信息(user_account)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class UserAccountDao extends ServiceImpl<UserAccountMapper, UserAccount> {

}