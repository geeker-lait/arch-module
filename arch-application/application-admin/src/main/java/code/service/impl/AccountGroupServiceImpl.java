package code.service.impl;

import code.dao.AccountGroupDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountGroupService;
import org.springframework.stereotype.Service;

/**
 * 账号-组织机构服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountGroupServiceImpl implements AccountGroupService {
    private final AccountGroupDao accountGroupDao;

}