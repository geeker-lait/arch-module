package code.service.impl;

import code.dao.AccountRoleMenuDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 账号-角色菜单服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRoleMenuServiceImpl implements AccountRoleMenuService {
    private final AccountRoleMenuDao accountRoleMenuDao;

}