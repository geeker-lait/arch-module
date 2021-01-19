package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountOauthToken;
import code.mapper.AccountOauthTokenMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-第三方账号授权(account_oauth_token)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountOauthTokenDao extends ServiceImpl<AccountOauthTokenMapper, AccountOauthToken> {

}