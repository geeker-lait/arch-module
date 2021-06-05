package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.client.AccountRoleApi;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.dto.RoleSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 CRUD 控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController("adminRoleController")
@RequestMapping("/rbac/role")
@Slf4j
@RequiredArgsConstructor
public class RoleApi implements FeignCrudApi<RoleSearchDto, Long, RoleRequest, AccountRoleApi> {

    private final AccountRoleApi roleFeignService;

    @Override
    public AccountRoleApi getFeignService() {
        return this.roleFeignService;
    }

}
