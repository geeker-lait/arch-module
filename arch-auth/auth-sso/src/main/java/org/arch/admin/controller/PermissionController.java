package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.FeignCrudController;
import org.arch.ums.account.dto.PermissionRequest;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.feign.account.client.UmsAccountPermissionFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor
public class PermissionController implements FeignCrudController<Permission, Long, PermissionRequest, UmsAccountPermissionFeignService> {

    private final UmsAccountPermissionFeignService permissionFeignService;

    @Override
    public UmsAccountPermissionFeignService getFeignService() {
        return this.permissionFeignService;
    }

    @Override
    public Permission getEntity() {
        return new Permission();
    }

}
