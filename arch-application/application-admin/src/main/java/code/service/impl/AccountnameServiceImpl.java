package code.service.impl;

import code.dao.AccountnameDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountnameService;
import org.springframework.stereotype.Service;

/**
 * 账号-名称服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountnameServiceImpl implements AccountnameService {
    private final AccountnameDao accountnameDao;

}