package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RoleResourceSearchDto;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.account.service.RoleResourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-角色资源表(RoleResource) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:29:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/resource")
public class RoleResourceController implements CrudController<RoleResource, java.lang.Long, RoleResourceSearchDto, RoleResourceService> {

    private final RoleResourceService roleResourceService;

    @Override
    public RoleResource resolver(TokenInfo token, RoleResource roleResource) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 roleResource 后返回 roleResource, 如: tenantId 的处理等.
        return roleResource;
    }

    @Override
    public RoleResourceService getCrudService() {
        return roleResourceService;
    }

    @Override
    public RoleResourceSearchDto getSearchDto() {
        return new RoleResourceSearchDto();
    }

}