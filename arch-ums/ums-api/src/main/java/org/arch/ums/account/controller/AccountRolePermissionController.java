package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.common.support.IController;
import org.arch.ums.account.service.AccountRolePermissionService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账号-角色权限表服务控制器
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/rolePermission")
public class AccountRolePermissionController implements IController {
    private final AccountRolePermissionService accountRolePermissionService;

}