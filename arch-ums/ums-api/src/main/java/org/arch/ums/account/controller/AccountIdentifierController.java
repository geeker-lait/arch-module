package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.arch.ums.account.entity.AccountIdentifier;
import org.arch.ums.account.service.AccountIdentifierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.StringUtils.hasText;

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
public class AccountIdentifierController{
    private final AccountIdentifierService accountIdentifierService;

    @GetMapping("/findByName/{identifier}")
    public AccountIdentifier findByName(@PathVariable(value = "identifier") String identifier) {
        // todo test
        QueryWrapper<AccountIdentifier> wrapper = new QueryWrapper<>();
        wrapper.eq(hasText(identifier), "identifier", identifier);

        return accountIdentifierService.getOne(wrapper);
    }

}
