package code.mapper;

import code.entity.AccountOauthToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-第三方账号授权(account_oauth_token)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface AccountOauthTokenMapper extends BaseMapper<AccountOauthToken> {

}
