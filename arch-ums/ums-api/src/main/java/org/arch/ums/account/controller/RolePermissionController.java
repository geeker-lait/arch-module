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
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-角色权限表(RolePermission) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:41:09
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/permission")
public class RolePermissionController implements CrudController<RolePermission, java.lang.Long, RolePermissionSearchDto, RolePermissionService> {

    private final TenantContextHolder tenantContextHolder;
    private final RolePermissionService rolePermissionService;

    @Override
    public RolePermission resolver(TokenInfo token, RolePermission rolePermission) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 rolePermission 后返回 rolePermission, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            rolePermission.setTenantId(token.getTenantId());
        }
        else {
            rolePermission.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
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