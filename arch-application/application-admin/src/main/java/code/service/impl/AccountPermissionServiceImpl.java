package code.service.impl;

import code.dao.AccountPermissionDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountPermissionService;
import org.springframework.stereotype.Service;

/**
 * 账号-权限服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountPermissionServiceImpl implements AccountPermissionService {
    private final AccountPermissionDao accountPermissionDao;

}