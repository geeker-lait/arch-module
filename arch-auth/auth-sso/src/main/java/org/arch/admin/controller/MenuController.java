package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.account.dto.MenuRequest;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.feign.account.client.UmsAccountMenuFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor
public class MenuController implements FeignCrudController<MenuSearchDto, Long, MenuRequest, UmsAccountMenuFeignService> {

    private final UmsAccountMenuFeignService menuFeignService;

    @Override
    public UmsAccountMenuFeignService getFeignService() {
        return this.menuFeignService;
    }

}
