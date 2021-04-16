package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.FeignCrudController;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.entity.Role;
import org.arch.ums.feign.account.client.UmsAccountRoleFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor
public class RoleController implements FeignCrudController<Role, Long, RoleRequest, UmsAccountRoleFeignService> {

    private final UmsAccountRoleFeignService roleFeignService;

    @Override
    public UmsAccountRoleFeignService getFeignService() {
        return this.roleFeignService;
    }

    @Override
    public Role getEntity() {
        return new Role();
    }

}
