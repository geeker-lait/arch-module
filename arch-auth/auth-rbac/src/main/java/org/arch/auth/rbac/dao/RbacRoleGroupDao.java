package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacRoleGroup;
import org.arch.auth.rbac.mapper.RbacRoleGroupMapper;
import org.springframework.stereotype.Repository;

/**
 * 角色组织或机构表(rbac_role_group)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacRoleGroupDao extends ServiceImpl<RbacRoleGroupMapper, RbacRoleGroup>  {

}