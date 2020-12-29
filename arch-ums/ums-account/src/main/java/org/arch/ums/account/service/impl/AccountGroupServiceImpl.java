package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountGroupDao;
import org.arch.ums.account.service.AccountGroupService;
import org.springframework.stereotype.Service;

/**
 * 账号-组织机构服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountGroupServiceImpl implements AccountGroupService {
    private final AccountGroupDao accountGroupDao;

}