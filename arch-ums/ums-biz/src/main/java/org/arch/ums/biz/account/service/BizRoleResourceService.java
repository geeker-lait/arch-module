package org.arch.ums.biz.account.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.ums.account.dao.RoleResourceDao;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.account.service.ResourceService;
import org.arch.ums.account.service.RoleResourceService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.stream.Collectors.toList;

/**
 * 账号-角色资源表(RoleResource) 表业务服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:43
 * @since 1.0.0
 */
@Slf4j
@Service
public class BizRoleResourceService extends RoleResourceService {
    private final ResourceService resourceService;
    private final BizAuthClientService bizAuthClientService;

    public BizRoleResourceService(RoleResourceDao roleResourceDao, ResourceService resourceService,
                                  BizAuthClientService bizAuthClientService) {
        super(roleResourceDao);
        this.resourceService = resourceService;
        this.bizAuthClientService = bizAuthClientService;
    }

    /**
     * 基于多租户, 更新角色 {@code roleId} 的权限资源
     *
     * @param tenantId      多租户 ID
     * @param roleId        角色 ID
     * @param resourceIds   权限资源 ids
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                   @NonNull Long roleId,
                                                   @NonNull List<Long> resourceIds) {
        List<RoleResource> roleResourceList =
                resourceIds.stream()
                           .map(resourceId -> new RoleResource().setResourceId(resourceId)
                                                                .setRoleId(roleId)
                                                                .setTenantId(tenantId.intValue())
                                                                .setDeleted(FALSE))
                           .collect(toList());

        Wrapper<RoleResource> roleResourceWrapper = Wrappers.lambdaQuery(RoleResource.class)
                                                            .eq(RoleResource::getTenantId, tenantId)
                                                            .eq(RoleResource::getRoleId, roleId)
                                                            .eq(RoleResource::getDeleted, FALSE);
        boolean removeResult = roleResourceDao.remove(roleResourceWrapper);
        if (!removeResult) {
            return false;
        }
        return roleResourceDao.saveBatch(roleResourceList);
    }

    /**
     * 基于 SCOPE, 更新角色 {@code roleId} 的 权限资源
     *
     * @param tenantId      多租户 ID
     * @param scopeId       SCOPE ID
     * @param roleId        角色 ID
     * @param resourceIds   权限资源 ids
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public boolean updateResourcesByRoleIdOfScopeId(@NonNull Long tenantId,
                                                    @NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    @NonNull List<Long> resourceIds) {
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (bizAuthClientService.hasRoleId(scopeId, roleId)) {
            return updateResourcesByRoleIdOfTenant(tenantId, roleId, resourceIds);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }

    /**
     * 基于多租户, 查询指定角色 {@code roleId} 的权限资源列表
     *
     * @param tenantId 多租户 ID
     * @param roleId   角色 ID
     * @return 权限资源列表
     */
    @Transactional(readOnly = true)
    @NonNull
    public List<Resource> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId, @NonNull Long roleId) {
        // tenantId 与 roleId 都是 long 类型, 不需要担心 sql 注入
        String subQuerySql = "SELECT `resource_id` FROM `account_role_resource` " +
                                "WHERE `tenant_id` = " + tenantId +
                                " AND `role_id` = " + roleId +
                                " AND `deleted` = 0";
        Wrapper<Resource> queryWrapper = Wrappers.lambdaQuery(Resource.class)
                                                 .eq(Resource::getTenantId, tenantId)
                                                 .eq(Resource::getDeleted, Boolean.FALSE)
                                                 .inSql(Resource::getId, subQuerySql);

        return this.resourceService.findAllBySpec(queryWrapper);
    }

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     * @param tenantId  多租户 ID
     * @param scopeId   SCOPE ID
     * @param roleId    角色 ID
     * @return  权限资源列表
     */
    @Transactional(readOnly = true)
    @NonNull
    public List<Resource> findAllResourcesByRoleIdOfScopeId(@NonNull Long tenantId,
                                                            @NonNull Long scopeId,
                                                            @NonNull Long roleId) {
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (bizAuthClientService.hasRoleId(scopeId, roleId)) {
            return findAllResourcesByRoleIdOfTenant(tenantId, roleId);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }
}
