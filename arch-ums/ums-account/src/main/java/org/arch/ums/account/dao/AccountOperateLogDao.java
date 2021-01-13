package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountOperateLog;
import org.arch.ums.account.mapper.AccountOperateLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号操作记录(account_operate_log)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountOperateLogDao extends ServiceImpl<AccountOperateLogMapper, AccountOperateLog> {

}