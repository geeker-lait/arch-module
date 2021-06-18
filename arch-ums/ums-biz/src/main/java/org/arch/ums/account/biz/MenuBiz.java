package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.MenuRequest;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.rest.MenuRest;
import org.arch.ums.account.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-菜单(Menu) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:03
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MenuBiz implements CrudBiz<MenuRequest, Menu, java.lang.Long, MenuSearchDto, MenuSearchDto, MenuService>, MenuRest {

    private final TenantContextHolder tenantContextHolder;
    private final MenuService menuService;

    @Override
    public Menu resolver(TokenInfo token, MenuRequest request) {
        Menu menu = new Menu();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, menu);
        }
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

    /**
     * 多租户根据 {@code menuIds} 获取 {@link Menu} 列表.
     *
     * @param tenantId 多租户 ID
     * @param menuIds  菜单 ID 列表
     * @return 菜单列表
     */
    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<MenuSearchDto> findByMenuIds(Integer tenantId, List<Long> menuIds) {
        Wrapper<Menu> menuWrapper = Wrappers.lambdaQuery(Menu.class)
                                            .eq(Menu::getTenantId, tenantId)
                                            .in(Menu::getId, menuIds)
                                            .eq(Menu::getDeleted, Boolean.FALSE);
        List<Menu> menuList = menuService.findAllBySpec(menuWrapper);
        return menuList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

}
