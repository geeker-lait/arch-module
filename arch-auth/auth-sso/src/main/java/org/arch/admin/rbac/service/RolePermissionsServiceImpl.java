package org.arch.admin.rbac.service;

import lombok.RequiredArgsConstructor;
import org.arch.framework.beans.Response;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.feign.account.client.UmsAccountRolePermissionFeignService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.permission.service.RolePermissionsService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * 权限更新权限服务实现,
 * 注意, 只实现与 {@link Permission} 相关的权限接口:  <br>
 * {@link #updateResourcesByRoleId(Long, Long...)} <br>
 * {@link #updateResourcesByRoleIdOfTenant(Long, Long, Long...)} <br>
 * {@link #updateResourcesByRoleIdOfScopeId(Long, Long, Long...)} <br>
 * {@link #findAllResourcesByRoleId(Long)} <br>
 * {@link #findAllResourcesByRoleIdOfScopeId(Long, Long)} <br>
 * {@link #findAllResourcesByRoleIdOfTenant(Long, Long)} <br>
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.7 15:37
 */
@Service
@RequiredArgsConstructor
public class RolePermissionsServiceImpl implements RolePermissionsService<Permission> {

    private final UmsAccountRolePermissionFeignService rolePermissionFeignService;
    private final TenantContextHolder tenantContextHolder;

    @Override
    public boolean updateResourcesByRoleId(@NonNull Long roleId, Long... resourceIds) throws RolePermissionsException {
        Long tenantId = Long.valueOf(tenantContextHolder.getTenantId());
        return updateResourcesByRoleIdOfTenant(tenantId, roleId, resourceIds);
    }

    @Override
    public boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                   @NonNull Long roleId,
                                                   Long... resourceIds) throws RolePermissionsException {
        try {
            Response<Boolean> response =
                    this.rolePermissionFeignService.updateResourcesByRoleIdOfTenant(tenantId, roleId, asList(resourceIds));
            return Optional.ofNullable(response.getSuccessData()).orElse(false);
        }
        catch (Exception e) {
            throw new RolePermissionsException(ErrorCodeEnum.UPDATE_ROLE_PERMISSIONS_FAILURE,
                                               tenantId + ":" + roleId + ":" + Arrays.toString(resourceIds), e);

        }
    }

    @NonNull
    @Override
    public boolean updateResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    Long... resourceIds) throws RolePermissionsException {
        try {
            Response<Boolean> response =
                    this.rolePermissionFeignService.updateResourcesByRoleIdOfScopeId(scopeId, roleId, asList(resourceIds));
            return Optional.ofNullable(response.getSuccessData()).orElse(false);
        }
        catch (Exception e) {
            throw new RolePermissionsException(ErrorCodeEnum.UPDATE_ROLE_PERMISSIONS_FAILURE,
                                               scopeId + ":" + roleId + ":" + Arrays.toString(resourceIds), e);

        }
    }

    @NonNull
    @Override
    public List<Permission> findAllResourcesByRoleId(@NonNull Long roleId) throws RolePermissionsException {
        Long tenantId = Long.valueOf(tenantContextHolder.getTenantId());
        return findAllResourcesByRoleIdOfTenant(tenantId, roleId);
    }

    @NonNull
    @Override
    public List<Permission> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                             @NonNull Long roleId) throws RolePermissionsException {
        try {
            Response<List<Permission>> response =
                    this.rolePermissionFeignService.findAllResourcesByRoleIdOfTenant(tenantId, roleId);
            return Optional.ofNullable(response.getSuccessData()).orElse(new ArrayList<>(0));
        }
        catch (Exception e) {
            throw new RolePermissionsException(ErrorCodeEnum.QUERY_ROLE_PERMISSIONS_FAILURE, tenantId + ":" + roleId, e);
        }
    }

    @NonNull
    @Override
    public List<Permission> findAllResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                              @NonNull Long roleId) throws RolePermissionsException {
        try {
            Response<List<Permission>> response =
                    this.rolePermissionFeignService.findAllResourcesByRoleIdOfScopeId(scopeId, roleId);
            return Optional.ofNullable(response.getSuccessData()).orElse(new ArrayList<>(0));
        }
        catch (Exception e) {
            throw new RolePermissionsException(ErrorCodeEnum.QUERY_ROLE_PERMISSIONS_FAILURE, scopeId + ":" + roleId, e);
        }
    }

    @NonNull
    @Override
    public Class<Permission> getUpdateResourcesClass() {
        return Permission.class;
    }
}
