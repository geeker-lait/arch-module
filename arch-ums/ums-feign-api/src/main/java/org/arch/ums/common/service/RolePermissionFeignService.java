package org.arch.ums.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.beans.Response;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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
 * @param <DTO> 实体类对应的 {@link BaseSearchDto}
 * @param <ID>  实体类的 id 类型
 * @param <R>   实体类对应的 {@code TRequest}
 */
public interface RolePermissionFeignService<P, PID extends Serializable, DTO, R, ID extends Serializable> {
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

    /**
     * 保存
     * @param request    实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @PostMapping
    Response<DTO> save(@Valid @RequestBody R request);

    /**
     * 批量保存
     * @param requestList 实体类对应的 {@code TRequest} 列表
     * @return  {@link Response}
     */
    @PostMapping("/saves")
    Response<List<DTO>> saveAll(@Valid @RequestBody List<R> requestList);

    /**
     * 根据 id 查询对象
     * @param id    id
     * @return  {@link Response}
     */
    @GetMapping(path = "/{id}")
    Response<DTO> findById(@PathVariable("id") ID id);

    /**
     * 根据 entity 条件查询对象
     * @param request    实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @GetMapping("/single")
    Response<DTO> findOne(@RequestBody R request);

    /**
     * 根据 entity 条件查询对象列表
     * @param request    实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @GetMapping("/find")
    Response<List<DTO>> find(@RequestBody R request);

    /**
     * 查询所有列表
     * @return  {@link Response}
     */
    @GetMapping("/list")
    Response<List<DTO>> list();

    /**
     * 分页查询
     * @param request       实体类对应的 {@code TRequest}
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @return  {@link Response}
     */
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    Response<Page<DTO>> page(@RequestBody R request,
                             @PathVariable(value = "pageNumber") Integer pageNumber,
                             @PathVariable(value = "pageSize") Integer pageSize);

    /**
     * 根据 id 删除
     * @param id    id
     * @return  {@link Response}
     */
    @DeleteMapping(path = "/{id}")
    Response<Boolean> deleteById(@PathVariable("id") ID id);

    /**
     * 根据 id 更新实体
     * @param request   实体类对应的 {@code TRequest}
     * @return  true 表示更新成功
     */
    @PutMapping
    Response<Boolean> updateById(@RequestBody R request);

    /**
     * 根据 entity 条件模糊查询对象; 模糊查询的条件拼接 {@code CONCAT("%", condition ,"%")},
     * 此方法会对不为 null 的 {@link String} 类型的字段都进行模糊查询.
     * @param request    实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @GetMapping("/like")
    Response<List<DTO>> like(@RequestBody R request);
}