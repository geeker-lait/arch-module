package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.dto.RolePermissionRequest;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.service.RolePermissionService;
import org.arch.ums.biz.account.service.BizRolePermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-角色权限表(RolePermission) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:27:01
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/permission")
public class RolePermissionBiz implements CrudBiz<RolePermissionRequest, RolePermission, Long, RolePermissionSearchDto, RolePermissionService> {

    private final TenantContextHolder tenantContextHolder;
    private final BizRolePermissionService bizRolePermissionService;

    @Override
    public RolePermission resolver(TokenInfo token, RolePermissionRequest request) {
        RolePermission rolePermission = new RolePermission();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, rolePermission);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            rolePermission.setTenantId(token.getTenantId());
        }
        else {
            rolePermission.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return rolePermission;
    }

    @Override
    public RolePermissionService getCrudService() {
        return bizRolePermissionService;
    }

    @Override
    public RolePermissionSearchDto getSearchDto() {
        return new RolePermissionSearchDto();
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 封装类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<RolePermissionSearchDto> findOne(@RequestBody @Valid RolePermissionRequest request, TokenInfo token) {
        try {
            RolePermission rolePermission = resolver(token, request);
            RolePermissionSearchDto searchDto = convertSearchDto(rolePermission);
            RolePermission result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertReturnDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 封装类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<RolePermissionSearchDto>> find(@RequestBody @Valid RolePermissionRequest request, TokenInfo token) {
        RolePermission rolePermission = resolver(token, request);
        RolePermissionSearchDto searchDto = convertSearchDto(rolePermission);
        try {
            List<RolePermission> rolePermissionList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(rolePermissionList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<RolePermissionSearchDto>> page(@RequestBody @Valid RolePermissionRequest request,
                                                         @PathVariable(value = "pageNumber") Integer pageNumber,
                                                         @PathVariable(value = "pageSize") Integer pageSize,
                                                         TokenInfo token) {
        RolePermission rolePermission = resolver(token, request);
        RolePermissionSearchDto searchDto = convertSearchDto(rolePermission);
        try {
            IPage<RolePermission> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertReturnDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 基于多租户, 更新角色 {@code roleId} 的 权限资源
     *
     * @param tenantId      多租户 ID
     * @param roleId        角色 ID
     * @param permissionIds 权限资源 ids
     * @return 是否更新成功
     */
    @PutMapping("/updateOfTenant/{tenantId}/{roleId}")
    @NonNull
    public Response<Boolean> updateResourcesByRoleIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                             @PathVariable(value = "roleId") Long roleId,
                                                             @RequestBody List<Long> permissionIds,
                                                             TokenInfo token) {

        // 必须是登录用户, 且 tenantId 必须与登录用户的 tenantId 相同
        if (isNull(token) || !(token.getTenantId().equals(tenantId.intValue()))) {
            Response.failed(AuthStatusCode.FORBIDDEN);
        }
        try {
            return Response.success(this.bizRolePermissionService.updateResourcesByRoleIdOfTenant(tenantId, roleId,
                                                                                                  permissionIds));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 基于 SCOPE, 更新角色 {@code roleId} 的 权限资源
     *
     * @param scopeId       SCOPE ID
     * @param roleId        角色 ID
     * @param permissionIds 权限资源 ids
     * @return 是否更新成功
     */
    @PutMapping("/updateOfScope/{scopeId}/{roleId}")
    @NonNull
    public Response<Boolean> updateResourcesByRoleIdOfScopeId(@PathVariable(value = "scopeId") Long scopeId,
                                                              @PathVariable(value = "roleId") Long roleId,
                                                              @RequestBody List<Long> permissionIds,
                                                              TokenInfo token) {

        // 必须是登录用户
        if (isNull(token)) {
            Response.failed(AuthStatusCode.FORBIDDEN);
        }
        try {
            return Response.success(this.bizRolePermissionService.updateResourcesByRoleIdOfScopeId(token.getTenantId()
                                                                                                        .longValue(),
                                                                                                   scopeId,
                                                                                                   roleId,
                                                                                                   permissionIds));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 基于多租户, 查询指定角色 {@code roleId} 的权限资源列表
     * @param tenantId  多租户 ID
     * @param roleId    角色 ID
     * @return  权限资源列表
     */
    @GetMapping("/findOfTenant/{tenantId}/{roleId}")
    @NonNull
    public Response<List<PermissionSearchDto>> findAllResourcesByRoleIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                                                @PathVariable(value = "roleId") Long roleId) {
        try {
            List<Permission> permissionList = this.bizRolePermissionService.findAllResourcesByRoleIdOfTenant(tenantId, roleId);
            List<PermissionSearchDto> menuSearchDtoList =
                    permissionList.stream()
                                  .map(permission -> {
                                      PermissionSearchDto searchDto = new PermissionSearchDto();
                                      BeanUtils.copyProperties(permission, searchDto);
                                      return searchDto;
                                  })
                                  .collect(Collectors.toList());
            return Response.success(menuSearchDtoList);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     *
     * @param scopeId SCOPE ID
     * @param roleId  角色 ID
     * @return 权限资源列表
     */
    @GetMapping("/findOfScope/{scopeId}/{roleId}")
    @NonNull
    public Response<List<PermissionSearchDto>> findAllResourcesByRoleIdOfScopeId(@PathVariable(value = "scopeId") Long scopeId,
                                                                                 @PathVariable(value = "roleId") Long roleId) {
        try {
            Long tenantId = Long.valueOf(tenantContextHolder.getTenantId());
            List<Permission> permissionList = this.bizRolePermissionService.findAllResourcesByRoleIdOfScopeId(tenantId,
                                                                                                              scopeId,
                                                                                                              roleId);
            List<PermissionSearchDto> menuSearchDtoList =
                    permissionList.stream()
                                  .map(permission -> {
                                      PermissionSearchDto searchDto = new PermissionSearchDto();
                                      BeanUtils.copyProperties(permission, searchDto);
                                      return searchDto;
                                  })
                                  .collect(Collectors.toList());
            return Response.success(menuSearchDtoList);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
