package code.mapper;

import code.entity.AccountRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-角色权限表(account_role_permission)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface AccountRolePermissionMapper extends BaseMapper<AccountRolePermission> {

}
