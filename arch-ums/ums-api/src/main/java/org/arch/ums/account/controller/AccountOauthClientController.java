package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.common.support.IController;
import org.arch.ums.account.service.AccountOauthClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 授权客户端服务控制器
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/oauthClient")
public class AccountOauthClientController implements IController {
    private final AccountOauthClientService accountOauthClientService;

    @GetMapping("/scopes")
    public Set<String> getScopesByAppIdAndAppCode(@RequestParam("appId") String appId,
                                                  @RequestParam("appCode") String appCode) {
        return accountOauthClientService.getScopesByAppIdAndAppCode(appId, appCode);
    }

}