package org.arch.auth.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.auth.rbac.entity.RbacUsername;

/**
 * 角色资源表(rbac_role_resource)数据Mapper
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
*/
@Mapper
public interface RbacUsernameMapper extends BaseMapper<RbacUsername> {

}
