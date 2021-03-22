package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.admin.rbac.service.RolePermissionsServiceImpl;
import org.arch.framework.beans.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rbac 权限控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.19 22:35
 */
@RestController
@RequestMapping("/rbac")
@Slf4j
@RequiredArgsConstructor
public class RbacController {

    private final RolePermissionsServiceImpl rolePermissionsService;

    @GetMapping
    public Response<Boolean> testRbacUpdateCache() {
        return Response.success(
                this.rolePermissionsService.updateResourcesByRoleIdOfTenant(0L,
                                                                            1L,
                                                                            1L, 2L, 3L));
    }

}
