package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountOperateLog;
import code.mapper.AccountOperateLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号操作记录(account_operate_log)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountOperateLogDao extends ServiceImpl<AccountOperateLogMapper, AccountOperateLog> {

}