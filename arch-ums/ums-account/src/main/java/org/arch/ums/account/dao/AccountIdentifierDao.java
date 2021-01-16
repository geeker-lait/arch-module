package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.entity.AccountIdentifier;
import org.arch.ums.account.mapper.AccountIdentifierMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * 用户-标识(account_identifier)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountIdentifierDao extends ServiceImpl<AccountIdentifierMapper, AccountIdentifier> {

    private AccountIdentifierMapper accountIdentifierMapper;

    /**
     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
     */
    @Nullable
    public AuthAccountDto getAccountByIdentifier(@NonNull String identifier) {
        return accountIdentifierMapper.getAccountByIdentifier(identifier);
    }
}