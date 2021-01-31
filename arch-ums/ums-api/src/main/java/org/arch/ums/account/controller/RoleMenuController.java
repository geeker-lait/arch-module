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

/**
 * 账号-角色菜单(RoleMenu) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:26:48
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/menu")
public class RoleMenuController implements CrudController<RoleMenu, java.lang.Long, RoleMenuSearchDto, RoleMenuService> {

    private final RoleMenuService roleMenuService;

    @Override
    public RoleMenu resolver(TokenInfo token, RoleMenu roleMenu) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 roleMenu 后返回 roleMenu, 如: tenantId 的处理等.
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