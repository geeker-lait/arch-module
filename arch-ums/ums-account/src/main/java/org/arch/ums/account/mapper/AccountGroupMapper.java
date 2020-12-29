package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-组织机构(account_group)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountGroupMapper extends BaseMapper<AccountGroup> {

}