package test.auth.ums.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.auth.ums.dto.AccountIdentifierSearchDto;
import test.auth.ums.entity.AccountIdentifier;
import test.auth.ums.service.AccountIdentifierService;

/**
 * 用户-标识(AccountIdentifier) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-26 22:59:13
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/identifier")
public class AccountIdentifierController implements CrudController<AccountIdentifier, java.lang.Long, AccountIdentifierSearchDto, AccountIdentifierService> {

    private final AccountIdentifierService accountIdentifierService;

    @Override
    public AccountIdentifier resolver(TokenInfo token, AccountIdentifier accountIdentifier) {
        return accountIdentifier;
    }

    @Override
    public AccountIdentifierService getCrudService() {
        return accountIdentifierService;
    }

    @Override
    public AccountIdentifierSearchDto getSearchDto() {
        return new AccountIdentifierSearchDto();
    }

}