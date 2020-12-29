package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountOauthClient;
import org.arch.ums.account.mapper.AccountOauthClientMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 授权客户端(account_oauth_client)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountOauthClientDao extends ServiceImpl<AccountOauthClientMapper, AccountOauthClient> {

}