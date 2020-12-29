package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountOauthClientDao;
import org.arch.ums.account.service.AccountOauthClientService;
import org.springframework.stereotype.Service;

/**
 * 授权客户端服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountOauthClientServiceImpl implements AccountOauthClientService {
    private final AccountOauthClientDao accountOauthClientDao;

}