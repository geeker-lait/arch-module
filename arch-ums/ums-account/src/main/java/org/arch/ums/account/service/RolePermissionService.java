package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RolePermissionDao;
import org.arch.ums.account.entity.RolePermission;
import org.springframework.stereotype.Service;

/**
 * 账号-角色权限表(RolePermission) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RolePermissionService extends CrudService<RolePermission, java.lang.Long> {
    private final RolePermissionDao rolePermissionDao;

}
