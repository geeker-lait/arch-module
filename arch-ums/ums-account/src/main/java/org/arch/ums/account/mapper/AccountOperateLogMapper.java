package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountOperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号操作记录(account_operat_log)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountOperateLogMapper extends BaseMapper<AccountOperateLog> {

}
