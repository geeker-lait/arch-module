package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.PermissionRequest;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-权限(Permission) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:08:01
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/permission")
public class PermissionController implements CrudController<PermissionRequest, Permission, java.lang.Long, PermissionSearchDto, PermissionService> {

    private final TenantContextHolder tenantContextHolder;
    private final PermissionService permissionService;

    @Override
    public Permission resolver(TokenInfo token, PermissionRequest request) {
        Permission permission = new Permission();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, permission);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            permission.setTenantId(token.getTenantId());
        }
        else {
            permission.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return permission;
    }

    @Override
    public PermissionService getCrudService() {
        return permissionService;
    }

    @Override
    public PermissionSearchDto getSearchDto() {
        return new PermissionSearchDto();
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<PermissionSearchDto> findOne(@RequestBody @Valid PermissionRequest request, TokenInfo token) {
        try {
            Permission permission = resolver(token, request);
            PermissionSearchDto searchDto = convertSearchDto(permission);
            Permission result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(convertSearchDto(result));
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
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<PermissionSearchDto>> find(@RequestBody @Valid PermissionRequest request, TokenInfo token) {
        Permission permission = resolver(token, request);
        PermissionSearchDto searchDto = convertSearchDto(permission);
        try {
            List<Permission> permissionList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(permissionList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<PermissionSearchDto>> page(@RequestBody @Valid PermissionRequest request,
                                                     @PathVariable(value = "pageNumber") Integer pageNumber,
                                                     @PathVariable(value = "pageSize") Integer pageSize,
                                                     TokenInfo token) {
        Permission permission = resolver(token, request);
        PermissionSearchDto searchDto = convertSearchDto(permission);
        try {
            IPage<Permission> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 多租户根据 {@code permissionIds} 获取 {@link Permission} 列表.
     *
     * @param tenantId      多租户 ID
     * @param permissionIds 权限 ID 列表
     * @return 权限列表, 只包含 {@code id, permissionCode, permissionUri, permissionVal} 字段
     */
    @GetMapping("/findByPermissionIds/{tenantId}")
    @NonNull
    public Response<List<PermissionSearchDto>> findByPermissionIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                                   @RequestBody List<Long> permissionIds) {
        Wrapper<Permission> permissionWrapper = Wrappers.lambdaQuery(Permission.class)
                                                        .select(Permission::getId,
                                                                Permission::getPermissionCode,
                                                                Permission::getPermissionUri,
                                                                Permission::getPermissionVal)
                                                        .eq(Permission::getTenantId, tenantId)
                                                        .in(Permission::getId, permissionIds)
                                                        .eq(Permission::getDeleted, Boolean.FALSE);
        try {
            List<Permission> permissionList = permissionService.findAllBySpec(permissionWrapper);
            return Response.success(permissionList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
