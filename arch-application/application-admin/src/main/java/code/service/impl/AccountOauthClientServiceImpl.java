package code.service.impl;

import code.dao.AccountOauthClientDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountOauthClientService;
import org.springframework.stereotype.Service;

/**
 * 账号-授权客户端服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountOauthClientServiceImpl implements AccountOauthClientService {
    private final AccountOauthClientDao accountOauthClientDao;

}