package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacRole;
import org.arch.auth.rbac.mapper.RbacRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * 角色表(rbac_role)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacRoleDao extends ServiceImpl<RbacRoleMapper, RbacRole> {

}