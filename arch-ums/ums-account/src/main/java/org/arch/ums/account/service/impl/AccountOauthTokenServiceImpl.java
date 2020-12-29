package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountOauthTokenDao;
import org.arch.ums.account.service.AccountOauthTokenService;
import org.springframework.stereotype.Service;

/**
 * 第三方账号授权服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountOauthTokenServiceImpl implements AccountOauthTokenService {
    private final AccountOauthTokenDao accountOauthTokenDao;

}