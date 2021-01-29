package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RoleSearchDto;
import org.arch.ums.account.entity.Role;
import org.arch.ums.account.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-角色(Role) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:23:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role")
public class RoleController implements CrudController<Role, java.lang.Long, RoleSearchDto, RoleService> {

    private final RoleService roleService;

    @Override
    public Role resolver(TokenInfo token, Role role) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 role 后返回 role, 如: tenantId 的处理等.
        return role;
    }

    @Override
    public RoleService getCrudService() {
        return roleService;
    }

    @Override
    public RoleSearchDto getSearchDto() {
        return new RoleSearchDto();
    }

}