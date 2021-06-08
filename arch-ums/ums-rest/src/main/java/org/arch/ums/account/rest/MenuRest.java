package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.MenuRequest;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号-菜单(Menu) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:20
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/menu")
public interface MenuRest extends CrudRest<MenuRequest, java.lang.Long, MenuSearchDto> {
    /**
     * 多租户根据 {@code menuIds} 获取 {@link Menu} 列表.
     *
     * @param tenantId 多租户 ID
     * @param menuIds  菜单 ID 列表
     * @return 菜单列表
     */
    @GetMapping("/findByMenuIds/{tenantId}")
    @NonNull
    List<MenuSearchDto> findByMenuIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                @RequestBody List<Long> menuIds);
}

