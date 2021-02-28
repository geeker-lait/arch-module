package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RoleMenuSearchDto;
import org.arch.ums.account.entity.RoleMenu;
import org.arch.ums.account.service.RoleMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-角色菜单(RoleMenu) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:16:08
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/menu")
public class RoleMenuController implements CrudController<RoleMenu, java.lang.Long, RoleMenuSearchDto, RoleMenuService> {

    private final TenantContextHolder tenantContextHolder;
    private final RoleMenuService roleMenuService;

    @Override
    public RoleMenu resolver(TokenInfo token, RoleMenu roleMenu) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            roleMenu.setTenantId(token.getTenantId());
        }
        else {
            roleMenu.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return roleMenu;
    }

    @Override
    public RoleMenuService getCrudService() {
        return roleMenuService;
    }

    @Override
    public RoleMenuSearchDto getSearchDto() {
        return new RoleMenuSearchDto();
    }

}
