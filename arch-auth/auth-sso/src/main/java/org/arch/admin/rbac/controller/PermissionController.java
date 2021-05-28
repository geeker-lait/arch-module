package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudController;
import org.arch.ums.account.client.AccountPermissionFeignService;
import org.arch.ums.account.dto.PermissionRequest;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限 CRUD 控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController("adminPermissionController")
@RequestMapping("/rbac/permission")
@Slf4j
@RequiredArgsConstructor
public class PermissionController implements FeignCrudController<PermissionSearchDto, Long, PermissionRequest, AccountPermissionFeignService> {

    private final AccountPermissionFeignService permissionFeignService;

    @Override
    public AccountPermissionFeignService getFeignService() {
        return this.permissionFeignService;
    }

}
