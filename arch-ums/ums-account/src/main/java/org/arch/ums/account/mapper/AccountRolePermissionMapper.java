package org.arch.ums.account.mapper;

import org.arch.ums.account.entity.AccountRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-角色权限表(account_role_permission)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountRolePermissionMapper extends BaseMapper<AccountRolePermission> {

}
