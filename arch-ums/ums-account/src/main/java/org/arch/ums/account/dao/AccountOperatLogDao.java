package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountOperatLog;
import org.arch.ums.account.mapper.AccountOperatLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号操作记录(account_operat_log)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountOperatLogDao extends ServiceImpl<AccountOperatLogMapper, AccountOperatLog> {

}