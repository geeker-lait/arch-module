package org.arch.ums.common.service;

import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * {@code top.dcenter.ums.security.core.api.permission.service.RolePermissionsService} 接口对应的通用 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.26 15:15
 *
 * @param <P>   需要返回的权限资源类型
 * @param <PID> 权限资源主键类型
 * @param <DTO> {@code BaseFeignService<T, ID>} 中的 DTO
 * @param <R>   {@code BaseFeignService<T, ID>} 中的 R
 * @param <ID>  {@code BaseFeignService<T, ID>} 中的 ID
 */
public interface RolePermissionFeignService<P, PID extends Serializable, DTO, R, ID extends Serializable>
                                                extends BaseFeignService<DTO, R, ID> {

    /**
     * 基于多租户, 更新角色 {@code roleId} 的 权限资源
     * @param tenantId      多租户 ID
     * @param roleId        角色 ID
     * @param resourceIds   权限资源 ids
     * @return  是否更新成功
     */
    @PutMapping("/updateOfTenant/{tenantId}/{roleId}")
    @NonNull
    Response<Boolean> updateResourcesByRoleIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                      @PathVariable(value = "roleId") Long roleId,
                                                      @RequestBody List<PID> resourceIds);

    /**
     * 基于 SCOPE, 更新角色 {@code roleId} 的 权限资源
     * @param scopeId       SCOPE ID
     * @param roleId        角色 ID
     * @param resourceIds   权限资源 ids
     * @return  是否更新成功
     */
    @PutMapping("/updateOfScope/{scopeId}/{roleId}")
    @NonNull
    Response<Boolean> updateResourcesByRoleIdOfScopeId(@PathVariable(value = "scopeId") Long scopeId,
                                                       @PathVariable(value = "roleId") Long roleId,
                                                       @RequestBody List<PID> resourceIds);

    /**
     * 基于多租户, 查询指定角色 {@code roleId} 的权限资源列表
     * @param tenantId  多租户 ID
     * @param roleId    角色 ID
     * @return  权限资源列表
     */
    @GetMapping("/findOfTenant/{tenantId}/{roleId}")
    @NonNull
    Response<List<P>> findAllResourcesByRoleIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                       @PathVariable(value = "roleId") Long roleId);

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     * @param scopeId   SCOPE ID
     * @param roleId    角色 ID
     * @return  权限资源列表
     */
    @GetMapping("/findOfScope/{scopeId}/{roleId}")
    @NonNull
    Response<List<P>> findAllResourcesByRoleIdOfScopeId(@PathVariable(value = "scopeId") Long scopeId,
                                                        @PathVariable(value = "roleId") Long roleId);

}