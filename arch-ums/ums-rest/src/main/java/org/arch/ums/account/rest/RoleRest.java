package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.dto.RoleSearchDto;
import org.arch.ums.account.entity.Role;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号-角色(Role) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:52:01
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/role")
public interface RoleRest extends CrudRest<RoleRequest, java.lang.Long, RoleSearchDto> {

    /**
     * 多租户根据 {@code roleIds} 获取 {@link Role} 列表.
     *
     * @param tenantId 多租户 ID
     * @param roleIds  角色 ID 列表
     * @return 角色列表
     */
    @GetMapping("/findByRoleIds/{tenantId}")
    @NonNull
    List<RoleSearchDto> findByMenuIds(@PathVariable(value = "tenantId") Integer tenantId,
                                      @RequestBody List<Long> roleIds);
}

