package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.service.IdentifierService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户-标识(Identifier) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:04:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/identifier")
public class IdentifierController implements CrudController<Identifier, java.lang.Long, IdentifierSearchDto, IdentifierService> {

    private final IdentifierService identifierService;

    @Override
    public Identifier resolver(TokenInfo token, Identifier identifier) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 identifier 后返回 identifier, 如: tenantId 的处理等.
        return identifier;
    }

    @Override
    public IdentifierService getCrudService() {
        return identifierService;
    }

    @Override
    public IdentifierSearchDto getSearchDto() {
        return new IdentifierSearchDto();
    }


//    /**
//     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
//     * @param identifier    用户唯一标识
//     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
//     */
//    @GetMapping("/loadAccount/{identifier}")
//    public AuthAccountDto loadAccountByIdentifier(@PathVariable(value = "identifier") String identifier) {
//        return accountIdentifierService.getAccountByIdentifier(identifier);
//    }

}