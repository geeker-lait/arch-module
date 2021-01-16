package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.common.support.IController;
import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.service.AccountIdentifierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户-标识服务控制器
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/identifier")
public class AccountIdentifierController implements IController {
    private final AccountIdentifierService accountIdentifierService;

    /**
     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
     */
    @GetMapping("/loadAccount/{identifier}")
    public AuthAccountDto loadAccountByIdentifier(@PathVariable(value = "identifier") String identifier) {
        return accountIdentifierService.getAccountByIdentifier(identifier);
    }

}