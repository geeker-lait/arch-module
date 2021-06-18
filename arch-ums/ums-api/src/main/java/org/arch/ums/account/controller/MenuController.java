package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.MenuRequest;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-菜单(Menu) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:03:21
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/menu")
public class MenuController implements CrudController<MenuRequest, Menu, java.lang.Long, MenuSearchDto, MenuService> {

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
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<MenuSearchDto> findOne(@RequestBody @Valid MenuRequest request, TokenInfo token) {
        try {
            Menu menu = resolver(token, request);
            MenuSearchDto searchDto = convertSearchDto(menu);
            Menu result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertSearchDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<MenuSearchDto>> find(@RequestBody @Valid MenuRequest request, TokenInfo token) {
        Menu menu = resolver(token, request);
        MenuSearchDto searchDto = convertSearchDto(menu);
        try {
            List<Menu> menuList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(menuList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<MenuSearchDto>> page(@RequestBody @Valid MenuRequest request,
                                               @PathVariable(value = "pageNumber") Integer pageNumber,
                                               @PathVariable(value = "pageSize") Integer pageSize,
                                               TokenInfo token) {
        Menu menu = resolver(token, request);
        MenuSearchDto searchDto = convertSearchDto(menu);
        try {
            IPage<Menu> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 多租户根据 {@code menuIds} 获取 {@link Menu} 列表.
     *
     * @param tenantId 多租户 ID
     * @param menuIds  菜单 ID 列表
     * @return 菜单列表
     */
    @GetMapping("/findByMenuIds/{tenantId}")
    @NonNull
    public Response<List<MenuSearchDto>> findByMenuIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                       @RequestBody List<Long> menuIds) {
        Wrapper<Menu> menuWrapper = Wrappers.lambdaQuery(Menu.class)
                                            .eq(Menu::getTenantId, tenantId)
                                            .in(Menu::getId, menuIds)
                                            .eq(Menu::getDeleted, Boolean.FALSE);
        try {
            List<Menu> menuList = menuService.findAllBySpec(menuWrapper);
            return Response.success(menuList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
