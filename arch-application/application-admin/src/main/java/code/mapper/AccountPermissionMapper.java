package code.mapper;

import code.entity.AccountPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-权限(account_permission)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface AccountPermissionMapper extends BaseMapper<AccountPermission> {

}
