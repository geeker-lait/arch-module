package org.arch.auth.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.auth.rbac.entity.RbacPermission;

/**
 * 权限表(rbac_permission)数据Mapper
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Mapper
public interface RbacPermissionMapper extends BaseMapper<RbacPermission> {

}
