package code.service.impl;

import code.dao.AccountRoleDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountRoleService;
import org.springframework.stereotype.Service;

/**
 * 账号-角色服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRoleServiceImpl implements AccountRoleService {
    private final AccountRoleDao accountRoleDao;

}