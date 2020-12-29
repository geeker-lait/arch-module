package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountOauthToken;
import org.arch.ums.account.mapper.AccountOauthTokenMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 第三方账号授权(account_oauth_token)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountOauthTokenDao extends ServiceImpl<AccountOauthTokenMapper, AccountOauthToken> {

}