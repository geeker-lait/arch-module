package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.mapper.RolePermissionMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色权限表(RolePermission) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:28:35
 * @since 1.0.0
 */
@Slf4j
@Repository
public class RolePermissionDao extends ServiceImpl<RolePermissionMapper, RolePermission> implements CrudDao<RolePermission> {

}