package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.common.support.IController;
import org.arch.ums.account.service.AccountMemberService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账号-会员账号服务控制器
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/member")
public class AccountMemberController implements IController {
    private final AccountMemberService accountMemberService;

}