package org.arch.application.admin.controller.ums.account;

import code.service.AccountRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账号-角色菜单服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accountRoleMenu")
public class AccountRoleMenuController{
    private final AccountRoleMenuService accountRoleMenuService;

}
