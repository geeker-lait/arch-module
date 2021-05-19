package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.dto.RoleSearchDto;
import org.arch.ums.account.client.AccountRoleFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/rbac/role")
@Slf4j
@RequiredArgsConstructor
public class RoleController implements FeignCrudController<RoleSearchDto, Long, RoleRequest, AccountRoleFeignService> {

    private final AccountRoleFeignService roleFeignService;

    @Override
    public AccountRoleFeignService getFeignService() {
        return this.roleFeignService;
    }

}
