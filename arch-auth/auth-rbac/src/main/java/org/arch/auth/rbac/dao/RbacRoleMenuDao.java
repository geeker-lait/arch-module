package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacRoleMenu;
import org.arch.auth.rbac.mapper.RbacRoleMenuMapper;
import org.springframework.stereotype.Repository;

/**
 * 角色菜单表(rbac_role_menu)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacRoleMenuDao extends ServiceImpl<RbacRoleMenuMapper, RbacRoleMenu> {

}