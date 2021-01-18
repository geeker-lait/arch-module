package code.service.impl;

import code.dao.AccountIdentifierDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountIdentifierService;
import org.springframework.stereotype.Service;

/**
 * 账号-标识服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountIdentifierServiceImpl implements AccountIdentifierService {
    private final AccountIdentifierDao accountIdentifierDao;

}