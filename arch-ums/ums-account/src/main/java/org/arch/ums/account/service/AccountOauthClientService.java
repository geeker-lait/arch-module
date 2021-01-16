package org.arch.ums.account.service;


import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * 授权客户端服务接口
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
public interface AccountOauthClientService {

    /**
     * 根据 appId 与 appCode 查询 scopes
     * @param appId     app id
     * @param appCode   app code
     * @return  返回 scope set
     */
    @NonNull
    Set<String> getScopesByAppIdAndAppCode(@NonNull String appId,
                                           @NonNull String appCode);
}
