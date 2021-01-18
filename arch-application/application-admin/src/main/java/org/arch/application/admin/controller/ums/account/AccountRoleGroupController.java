package org.arch.application.admin.controller.ums.account;

import code.service.AccountRoleGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账号-角色组织或机构服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accountRoleGroup")
public class AccountRoleGroupController{
    private final AccountRoleGroupService accountRoleGroupService;

}
