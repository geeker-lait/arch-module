package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountOauthToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 第三方账号授权(account_oauth_token)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountOauthTokenMapper extends BaseMapper<AccountOauthToken> {

}
