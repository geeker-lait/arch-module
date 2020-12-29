package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountRoleDao;
import org.arch.ums.account.service.AccountRoleService;
import org.springframework.stereotype.Service;

/**
 * 账号-角色服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRoleServiceImpl implements AccountRoleService {
    private final AccountRoleDao accountRoleDao;

}