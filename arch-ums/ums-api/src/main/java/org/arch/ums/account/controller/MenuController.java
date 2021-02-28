package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-菜单(Menu) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:15:00
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/menu")
public class MenuController implements CrudController<Menu, java.lang.Long, MenuSearchDto, MenuService> {

    private final TenantContextHolder tenantContextHolder;
    private final MenuService menuService;

    @Override
    public Menu resolver(TokenInfo token, Menu menu) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            menu.setTenantId(token.getTenantId());
        }
        else {
            menu.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return menu;
    }

    @Override
    public MenuService getCrudService() {
        return menuService;
    }

    @Override
    public MenuSearchDto getSearchDto() {
        return new MenuSearchDto();
    }

}
