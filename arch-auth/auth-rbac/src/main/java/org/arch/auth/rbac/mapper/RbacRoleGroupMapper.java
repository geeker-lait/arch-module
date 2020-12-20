package org.arch.auth.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.auth.rbac.entity.RbacRoleGroup;

/**
 * 角色组织或机构表(rbac_role_group)数据Mapper
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
*/
@Mapper
public interface RbacRoleGroupMapper extends BaseMapper<RbacRoleGroup> {

}
