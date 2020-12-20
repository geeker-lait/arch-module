package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacMenu;
import org.arch.auth.rbac.mapper.RbacMenuMapper;
import org.springframework.stereotype.Repository;

/**
 * 权限表(rbac_menu)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacMenuDao extends ServiceImpl<RbacMenuMapper, RbacMenu> {

}