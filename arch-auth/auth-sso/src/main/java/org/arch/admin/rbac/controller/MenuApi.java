package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.client.AccountMenuApi;
import org.arch.ums.account.dto.MenuRequest;
import org.arch.ums.account.dto.MenuSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单 CRUD 控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController("adminMenuController")
@RequestMapping("/rbac/menu")
@Slf4j
@RequiredArgsConstructor
public class MenuApi implements FeignCrudApi<MenuSearchDto, Long, MenuRequest, AccountMenuApi> {

    private final AccountMenuApi menuFeignService;

    @Override
    public AccountMenuApi getFeignService() {
        return this.menuFeignService;
    }

}
