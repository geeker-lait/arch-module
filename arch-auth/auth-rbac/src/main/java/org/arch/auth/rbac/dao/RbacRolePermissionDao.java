package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacRolePermission;
import org.arch.auth.rbac.mapper.RbacRolePermissionMapper;
import org.springframework.stereotype.Repository;

/**
 * 角色权限表(rbac_role_permission)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacRolePermissionDao extends ServiceImpl<RbacRolePermissionMapper, RbacRolePermission> {

}