package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleGroupDao;
import org.arch.ums.account.entity.RoleGroup;
import org.springframework.stereotype.Service;

/**
 * 账号-角色组织或机构(RoleGroup) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleGroupService extends CrudService<RoleGroup, java.lang.Long> {
    private final RoleGroupDao roleGroupDao;


}
