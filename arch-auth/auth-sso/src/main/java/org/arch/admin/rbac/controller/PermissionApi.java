package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountPermissionApi;
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
public class PermissionApi implements FeignCrudApi<PermissionSearchDto, Long, PermissionRequest, AccountPermissionApi> {

    private final AccountPermissionApi accountPermissionApi;

    @Override
    public AccountPermissionApi getFeignApi() {
        return this.accountPermissionApi;
    }

}
