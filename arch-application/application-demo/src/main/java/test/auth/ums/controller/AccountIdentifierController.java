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
 * @date 2021-01-24 23:17:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accountIdentifier")
public class AccountIdentifierController implements CrudController<AccountIdentifier, java.lang.Long, AccountIdentifierSearchDto, AccountIdentifierService> {

    private final AccountIdentifierService accountIdentifierService;

    @Override
    public AccountIdentifierService getCrudService() {
        return accountIdentifierService;
    }

    @Override
    public AccountIdentifierSearchDto getSearchDto() {
        return new AccountIdentifierSearchDto();
    }

    @Override
    public AccountIdentifier resolver(TokenInfo token, AccountIdentifier accountIdentifier) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 实体类 后返回 实体类对象, 如: tenantId 的处理等.
        return accountIdentifier;
    }

}