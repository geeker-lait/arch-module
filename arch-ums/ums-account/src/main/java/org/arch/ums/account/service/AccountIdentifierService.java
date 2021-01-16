package org.arch.ums.account.service;


import org.arch.ums.account.dto.AuthAccountDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 用户-标识服务接口
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
public interface AccountIdentifierService {

    /**
     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
     */
    @Nullable
    AuthAccountDto getAccountByIdentifier(@NonNull String identifier);
}
