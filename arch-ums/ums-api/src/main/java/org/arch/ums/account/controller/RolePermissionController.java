package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.service.RolePermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-角色权限表(RolePermission) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:28:41
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/permission")
public class RolePermissionController implements CrudController<RolePermission, java.lang.Longjava.lang.Longjava.lang.Long, RolePermissionSearchDto, RolePermissionService> {

    private final RolePermissionService rolePermissionService;

    @Override
    public RolePermission resolver(TokenInfo token, RolePermission rolePermission) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 rolePermission 后返回 rolePermission, 如: tenantId 的处理等.
        return rolePermission;
    }

    @Override
    public RolePermissionService getCrudService() {
        return rolePermissionService;
    }

    @Override
    public RolePermissionSearchDto getSearchDto() {
        return new RolePermissionSearchDto();
    }

}