package org.arch.admin.rbac.service;

import lombok.RequiredArgsConstructor;
import org.arch.framework.beans.Response;
import org.arch.ums.account.client.AccountRoleGroupFeignService;
import org.arch.ums.account.entity.Group;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.permission.service.RolePermissionsService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * 角色组更新权限服务实现,
 * 注意, 只实现与 {@link Group} 相关的权限接口: <br>
 * {@link #updateRolesByGroupId(Long, Long...)} <br>
 * {@link #updateRolesByGroupIdOfTenant(Long, Long, Long...)} <br>
 * {@link #findRolesByGroupId(Long)} <br>
 * {@link #findRolesByGroupIdOfTenant(Long, Long)} <br>
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.7 15:37
 */
@Service
@RequiredArgsConstructor
public class RoleGroupPermissionsServiceImpl implements RolePermissionsService<Group> {

    private final AccountRoleGroupFeignService roleGroupFeignService;
    private final TenantContextHolder tenantContextHolder;

    @NonNull
    @Override
    public boolean updateRolesByGroupId(@NonNull Long groupId,
                                        Long... roleIds) throws RolePermissionsException {
        Long tenantId = Long.valueOf(tenantContextHolder.getTenantId());
        return updateRolesByGroupIdOfTenant(tenantId, groupId, roleIds);
    }

    @NonNull
    @Override
    public boolean updateRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                @NonNull Long groupId,
                                                Long... roleIds) throws RolePermissionsException {
        try {
            Response<Boolean> response = this.roleGroupFeignService.updateRolesByGroupIdOfTenant(tenantId,
                    groupId,
                    asList(roleIds));
            return Optional.ofNullable(response.getSuccessData()).orElse(false);
        } catch (Exception e) {
            throw new RolePermissionsException(ErrorCodeEnum.UPDATE_ROLE_PERMISSIONS_FAILURE,
                    tenantId + ":" + groupId + ":" + Arrays.toString(roleIds), e);

        }
    }

    @NonNull
    @Override
    public Set<String> findRolesByGroupId(@NonNull Long groupId) throws RolePermissionsException {
        Long tenantId = Long.valueOf(tenantContextHolder.getTenantId());
        return findRolesByGroupIdOfTenant(tenantId, groupId);
    }

    @NonNull
    @Override
    public Set<String> findRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                  @NonNull Long groupId) throws RolePermissionsException {
        try {
            Response<Set<String>> response = this.roleGroupFeignService.findRolesByGroupIdOfTenant(tenantId, groupId);
            return Optional.ofNullable(response.getSuccessData()).orElse(new HashSet<>(0));
        } catch (Exception e) {
            throw new RolePermissionsException(ErrorCodeEnum.QUERY_ROLE_PERMISSIONS_FAILURE, tenantId + ":" + groupId, e);
        }
    }

    @NonNull
    @Override
    public Class<Group> getUpdateResourcesClass() {
        return Group.class;
    }
}
