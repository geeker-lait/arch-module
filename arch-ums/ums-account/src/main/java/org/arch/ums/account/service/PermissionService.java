package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Permission;
import org.springframework.stereotype.Service;

/**
 * 账号-权限(Permission) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:18:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionService extends CrudService<Permission, java.lang.Long> {

}