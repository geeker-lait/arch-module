package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountIdentifier;
import org.arch.ums.account.mapper.AccountIdentifierMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AccountIdentifierDao extends ServiceImpl<AccountIdentifierMapper, AccountIdentifier> {

}