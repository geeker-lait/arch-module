package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountIdentifierDao;
import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.service.AccountIdentifierService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 用户-标识服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountIdentifierServiceImpl implements AccountIdentifierService {
    private final AccountIdentifierDao accountIdentifierDao;

    @Override
    public AuthAccountDto getAccountByIdentifier(@NonNull String identifier) {
        return accountIdentifierDao.getAccountByIdentifier(identifier);
    }
}