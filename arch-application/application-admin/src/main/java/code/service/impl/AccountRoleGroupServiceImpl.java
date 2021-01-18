package code.service.impl;

import code.dao.AccountRoleGroupDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountRoleGroupService;
import org.springframework.stereotype.Service;

/**
 * 账号-角色组织或机构服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRoleGroupServiceImpl implements AccountRoleGroupService {
    private final AccountRoleGroupDao accountRoleGroupDao;

}