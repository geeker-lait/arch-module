package org.arch.admin.rbac.service;

import org.arch.ums.account.entity.Permission;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.premission.service.RolePermissionsService;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 权限更新权限服务实现
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.7 15:37
 */
@Service
public class RolePermissionsServiceImpl implements RolePermissionsService<Permission> {

    @Override
    public boolean updateResourcesByRoleId(@NonNull Long roleId, Long... resourceIds) throws RolePermissionsException {
        // TODO
        return true;
    }

    @Override
    public boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                   @NonNull Long roleId,
                                                   Long... resourceIds) throws RolePermissionsException {
        // TODO
        return true;
    }

    @NonNull
    @Override
    public boolean updateResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    Long... resourceIds) throws RolePermissionsException {
        // TODO
        return true;
    }

    @NonNull
    @Override
    public boolean updateRolesByGroupId(@NonNull Long groupId, Long... roleIds) throws RolePermissionsException {
        // TODO
        return true;
    }

    @NonNull
    @Override
    public boolean updateRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                @NonNull Long groupId,
                                                @NonNull Long... roleIds) throws RolePermissionsException {
        // TODO
        return true;
    }

    @NonNull
    @Override
    public List<Permission> findAllResourcesByRoleId(@NonNull Long roleId) throws RolePermissionsException {
        // TODO
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<Permission> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                             @NonNull Long roleId) throws RolePermissionsException {
        // TODO
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<Permission> findAllResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                              @NonNull Long roleId) throws RolePermissionsException {
        // TODO
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public Set<String> findRolesByGroupId(@NonNull Long groupId) throws RolePermissionsException {
        // TODO
        return Collections.emptySet();
    }

    @NonNull
    @Override
    public Set<String> findRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                  @NonNull Long groupId) throws RolePermissionsException {
        // TODO
        return Collections.emptySet();
    }

    @NonNull
    @Override
    public Class<Permission> getUpdateResourcesClass() {
        return Permission.class;
    }
}
