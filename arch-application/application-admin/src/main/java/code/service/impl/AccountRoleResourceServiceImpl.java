package code.service.impl;

import code.dao.AccountRoleResourceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * 账号-角色资源表服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRoleResourceServiceImpl implements AccountRoleResourceService {
    private final AccountRoleResourceDao accountRoleResourceDao;

}