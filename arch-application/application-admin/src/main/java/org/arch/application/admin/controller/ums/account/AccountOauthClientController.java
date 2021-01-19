package org.arch.application.admin.controller.ums.account;

import code.service.AccountOauthClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账号-授权客户端服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accountOauthClient")
public class AccountOauthClientController{
    private final AccountOauthClientService accountOauthClientService;

}
