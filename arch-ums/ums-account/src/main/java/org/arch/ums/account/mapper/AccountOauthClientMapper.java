package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountOauthClient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 授权客户端(account_oauth_client)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountOauthClientMapper extends BaseMapper<AccountOauthClient> {

}
