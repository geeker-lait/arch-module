package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.dto.RoleSearchDto;
import org.arch.ums.account.entity.Role;
import org.arch.ums.account.service.RoleService;
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
 * 账号-角色(Role) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:13:46
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role")
public class RoleBiz implements CrudBiz<RoleRequest, Role, Long, RoleSearchDto, RoleService> {

    private final TenantContextHolder tenantContextHolder;
    private final RoleService roleService;

    @Override
    public Role resolver(TokenInfo token, RoleRequest request) {
        Role role = new Role();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, role);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            role.setTenantId(token.getTenantId());
        }
        else {
            role.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return role;
    }

    @Override
    public RoleService getCrudService() {
        return roleService;
    }

    @Override
    public RoleSearchDto getSearchDto() {
        return new RoleSearchDto();
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
    public Response<RoleSearchDto> findOne(@RequestBody @Valid RoleRequest request, TokenInfo token) {
        try {
            Role role = resolver(token, request);
            RoleSearchDto searchDto = convertSearchDto(role);
            Role result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<RoleSearchDto>> find(@RequestBody @Valid RoleRequest request, TokenInfo token) {
        Role role = resolver(token, request);
        RoleSearchDto searchDto = convertSearchDto(role);
        try {
            List<Role> roleList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(roleList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
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
     * @param request    实体的 request 封装类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<RoleSearchDto>> page(@RequestBody @Valid RoleRequest request,
                                               @PathVariable(value = "pageNumber") Integer pageNumber,
                                               @PathVariable(value = "pageSize") Integer pageSize,
                                               TokenInfo token) {
        Role role = resolver(token, request);
        RoleSearchDto searchDto = convertSearchDto(role);
        try {
            IPage<Role> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertReturnDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 多租户根据 {@code roleIds} 获取 {@link Role} 列表.
     *
     * @param tenantId 多租户 ID
     * @param roleIds  角色 ID 列表
     * @return 角色列表
     */
    @GetMapping("/findByRoleIds/{tenantId}")
    @NonNull
    public Response<List<RoleSearchDto>> findByMenuIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                       @RequestBody List<Long> roleIds) {
        Wrapper<Role> roleWrapper = Wrappers.lambdaQuery(Role.class)
                                            .eq(Role::getTenantId, tenantId)
                                            .in(Role::getId, roleIds)
                                            .eq(Role::getDeleted, Boolean.FALSE);
        try {
            List<Role> roleList = roleService.findAllBySpec(roleWrapper);
            return Response.success(roleList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
