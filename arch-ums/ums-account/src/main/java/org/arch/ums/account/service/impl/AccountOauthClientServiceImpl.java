package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountOauthClientDao;
import org.arch.ums.account.service.AccountOauthClientService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.StringUtils.commaDelimitedListToStringArray;
import static org.springframework.util.StringUtils.hasText;

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

    @NonNull
    @Override
    public Set<String> getScopesByAppIdAndAppCode(@NonNull String appId, @NonNull String appCode) {
        String scopes = accountOauthClientDao.getScopes(appId, appCode);
        if (!hasText(scopes)) {
            return Collections.emptySet();
        }
        String[] tokens = commaDelimitedListToStringArray(scopes);
        return new HashSet<>(Arrays.asList(tokens));
    }
}