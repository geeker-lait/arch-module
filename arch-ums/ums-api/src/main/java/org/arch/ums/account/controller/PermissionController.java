package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-权限(Permission) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:39:17
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/permission")
public class PermissionController implements CrudController<Permission, Long, PermissionSearchDto, PermissionService> {

    private final TenantContextHolder tenantContextHolder;
    private final PermissionService permissionService;

    @Override
    public Permission resolver(TokenInfo token, Permission permission) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 permission 后返回 permission, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            permission.setTenantId(token.getTenantId());
        }
        else {
            permission.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return permission;
    }

    @Override
    public PermissionService getCrudService() {
        return permissionService;
    }

    @Override
    public PermissionSearchDto getSearchDto() {
        return new PermissionSearchDto();
    }

}