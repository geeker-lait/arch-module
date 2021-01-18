package code.service.impl;

import code.dao.AccountMenuDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountMenuService;
import org.springframework.stereotype.Service;

/**
 * 账号-菜单服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountMenuServiceImpl implements AccountMenuService {
    private final AccountMenuDao accountMenuDao;

}