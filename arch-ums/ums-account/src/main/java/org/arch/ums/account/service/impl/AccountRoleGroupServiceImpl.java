package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountRoleGroupDao;
import org.arch.ums.account.service.AccountRoleGroupService;
import org.springframework.stereotype.Service;

/**
 * 账号-角色组织或机构服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRoleGroupServiceImpl implements AccountRoleGroupService {
    private final AccountRoleGroupDao accountRoleGroupDao;

}