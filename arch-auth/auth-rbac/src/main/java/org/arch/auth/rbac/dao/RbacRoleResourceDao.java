package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacRoleResource;
import org.arch.auth.rbac.mapper.RbacRoleResourceMapper;
import org.springframework.stereotype.Repository;

/**
 * 角色资源表(rbac_role_resource)数据DAO
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Slf4j
@Repository
public class RbacRoleResourceDao extends ServiceImpl<RbacRoleResourceMapper, RbacRoleResource> {

}