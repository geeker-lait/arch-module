package code.service.impl;

import code.dao.AccountOauthTokenDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountOauthTokenService;
import org.springframework.stereotype.Service;

/**
 * 账号-第三方账号授权服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountOauthTokenServiceImpl implements AccountOauthTokenService {
    private final AccountOauthTokenDao accountOauthTokenDao;

}