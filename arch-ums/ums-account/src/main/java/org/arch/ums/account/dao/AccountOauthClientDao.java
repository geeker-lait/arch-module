package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountOauthClient;
import org.arch.ums.account.mapper.AccountOauthClientMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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
@RequiredArgsConstructor
public class AccountOauthClientDao extends ServiceImpl<AccountOauthClientMapper, AccountOauthClient> {

    private final AccountOauthClientMapper accountOauthClientMapper;

    /**
     * 根据 appId 与 appCode 获取 scopes
     * @param appId     app id
     * @param appCode   app code
     * @return  返回 scope 字符串, 可能未 null
     */
    @Nullable
    public String getScopes(@NonNull String appId, @NonNull String appCode) {
        return accountOauthClientMapper.getScopes(appId, appCode);
    }
}