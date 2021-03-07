package org.arch.auth.sso.rbac.service;

import org.arch.ums.account.entity.Resource;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.api.premission.service.RolePermissionsService;
import top.dcenter.ums.security.core.exception.RolePermissionsException;

import java.util.List;
import java.util.Set;

/**
 * 资源更新权限服务实现
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.7 15:37
 */
public class RoleResourcePermissionsServiceImpl implements RolePermissionsService<Resource> {

    @Override
    public boolean updateResourcesByRoleId(@NonNull Long roleId, Long... resourceIds) throws RolePermissionsException {
        // TODO
        return false;
    }

    @Override
    public boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                   @NonNull Long roleId,
                                                   Long... resourceIds) throws RolePermissionsException {
        // TODO
        return false;
    }

    @NonNull
    @Override
    public boolean updateResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    Long... resourceIds) throws RolePermissionsException {
        // TODO
        return false;
    }

    @NonNull
    @Override
    public boolean updateRolesByGroupId(@NonNull Long groupId,
                                        Long... roleIds) throws RolePermissionsException {
        // TODO
        return false;
    }

    @NonNull
    @Override
    public boolean updateRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                @NonNull Long groupId,
                                                Long... roleIds) throws RolePermissionsException {
        // TODO
        return false;
    }

    @NonNull
    @Override
    public List<Resource> findAllResourcesByRoleId(@NonNull Long roleId) throws RolePermissionsException {
        // TODO
        return null;
    }



    @NonNull
    @Override
    public List<Resource> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                           @NonNull Long roleId) throws RolePermissionsException {
        // TODO
        return null;
    }

    @NonNull
    @Override
    public List<Resource> findAllResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                            @NonNull Long roleId) throws RolePermissionsException {
        // TODO
        return null;
    }

    @NonNull
    @Override
    public Set<String> findRolesByGroupId(@NonNull Long groupId) throws RolePermissionsException {
        // TODO
        return null;
    }

    @NonNull
    @Override
    public Set<String> findRolesByGroupIdOfTenant(@NonNull Long tenantId,
                                                  @NonNull Long groupId) throws RolePermissionsException {
        // TODO
        return null;
    }


    @NonNull
    @Override
    public Class<Resource> getUpdateResourcesClass() {
        return Resource.class;
    }
}
