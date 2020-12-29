package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-权限(account_permission)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountPermissionMapper extends BaseMapper<AccountPermission> {

}
