package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 账号-菜单(Menu) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:38:21
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/menu")
public class MenuController implements CrudController<Menu, Long, MenuSearchDto, MenuService> {

    private final AppProperties appProperties;
    private final MenuService menuService;

    @Override
    public Menu resolver(TokenInfo token, Menu menu) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 menu 后返回 menu, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            menu.setTenantId(token.getTenantId());
        }
        else {
            menu.setTenantId(appProperties.getSystemTenantId());
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