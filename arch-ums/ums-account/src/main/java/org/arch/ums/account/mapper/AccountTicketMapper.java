package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-券(account_ticket)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountTicketMapper extends BaseMapper<AccountTicket> {

}
