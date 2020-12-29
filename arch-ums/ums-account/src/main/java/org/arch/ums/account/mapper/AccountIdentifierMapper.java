package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountIdentifier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-标识(account_identifier)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountIdentifierMapper extends BaseMapper<AccountIdentifier> {

}
