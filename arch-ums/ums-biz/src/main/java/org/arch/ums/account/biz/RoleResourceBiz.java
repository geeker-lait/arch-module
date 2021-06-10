package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dao.RoleResourceDao;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.dto.RoleResourceRequest;
import org.arch.ums.account.dto.RoleResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.entity.RoleResource;
import org.arch.ums.account.rest.RoleResourceRest;
import org.arch.ums.account.service.ResourceService;
import org.arch.ums.account.service.RoleResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * 账号-角色资源表(RoleResource) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:06
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleResourceBiz implements CrudBiz<RoleResourceRequest, RoleResource, java.lang.Long, RoleResourceSearchDto, RoleResourceSearchDto, RoleResourceService>, RoleResourceRest {

    private final TenantContextHolder tenantContextHolder;
    private final RoleResourceService roleResourceService;
    private final AuthClientBiz authClientBiz;
    private final RoleResourceDao roleResourceDao;
    private final ResourceService resourceService;

    @Override
    public RoleResource resolver(TokenInfo token, RoleResourceRequest request) {
        RoleResource roleResource = new RoleResource();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, roleResource);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            roleResource.setTenantId(token.getTenantId());
        }
        else {
            roleResource.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return roleResource;
    }

    @Override
    public RoleResourceService getCrudService() {
        return roleResourceService;
    }

    @Override
    public RoleResourceSearchDto getSearchDto() {
        return new RoleResourceSearchDto();
    }

    /**
     * 基于多租户, 更新角色 {@code roleId} 的权限资源
     *
     * @param tenantId      多租户 ID
     * @param roleId        角色 ID
     * @param resourceIds   权限资源 ids
     * @return 是否更新成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public Boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
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
     * @param scopeId       SCOPE ID
     * @param roleId        角色 ID
     * @param resourceIds   权限资源 ids
     * @return 是否更新成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public Boolean updateResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    @NonNull List<Long> resourceIds) {
        String tenantId = tenantContextHolder.getTenantId();
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientBiz.hasRoleId(scopeId, roleId)) {
            return updateResourcesByRoleIdOfTenant(Long.valueOf(tenantId), roleId, resourceIds);
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
    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<ResourceSearchDto> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId, @NonNull Long roleId) {
        // tenantId 与 roleId 都是 long 类型, 不需要担心 sql 注入
        String subQuerySql = "SELECT `resource_id` FROM `account_role_resource` " +
                "WHERE `tenant_id` = " + tenantId +
                " AND `role_id` = " + roleId +
                " AND `deleted` = 0";
        Wrapper<Resource> queryWrapper = Wrappers.lambdaQuery(Resource.class)
                                                 .eq(Resource::getTenantId, tenantId)
                                                 .eq(Resource::getDeleted, Boolean.FALSE)
                                                 .inSql(Resource::getId, subQuerySql);

        List<Resource> resourceList = this.resourceService.findAllBySpec(queryWrapper);
        return resourceList.stream()
                .map(resource -> {
                    ResourceSearchDto resourceSearchDto = new ResourceSearchDto();
                    BeanUtils.copyProperties(resource, resourceSearchDto);
                    return resourceSearchDto;
                })
                .collect(toList());
    }

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     * @param scopeId   SCOPE ID
     * @param roleId    角色 ID
     * @return  权限资源列表
     */
    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<ResourceSearchDto> findAllResourcesByRoleIdOfScopeId(@NonNull Long scopeId, @NonNull Long roleId) {
        String tenantId = tenantContextHolder.getTenantId();
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientBiz.hasRoleId(scopeId, roleId)) {
            return findAllResourcesByRoleIdOfTenant(Long.valueOf(tenantId), roleId);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }

}
