package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.PermissionRequest;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号-权限(Permission) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:40
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/permission")
public interface PermissionRest extends CrudRest<PermissionRequest, java.lang.Long, PermissionSearchDto> {
    /**
     * 多租户根据 {@code permissionIds} 获取 {@link Permission} 列表.
     *
     * @param tenantId      多租户 ID
     * @param permissionIds 权限 ID 列表
     * @return 权限列表, 只包含 {@code id, permissionCode, permissionUri, permissionVal} 字段
     */
    @GetMapping("/findByPermissionIds/{tenantId}")
    @NonNull
    List<PermissionSearchDto> findByPermissionIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                  @RequestBody List<Long> permissionIds);
}

