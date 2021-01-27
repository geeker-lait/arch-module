package test.auth.ums.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;
import test.auth.ums.entity.AccountIdentifier;

/**
 * 用户-标识(AccountIdentifier) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-26 22:59:13
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountIdentifierService extends CrudService<AccountIdentifier, java.lang.Long> {

}