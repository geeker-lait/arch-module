package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.PermissionRequest;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.rest.PermissionRest;
import org.arch.ums.account.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-权限(Permission) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:03
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionBiz implements CrudBiz<PermissionRequest, Permission, java.lang.Long, PermissionSearchDto, PermissionSearchDto, PermissionService>, PermissionRest {

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public PermissionSearchDto findOne(PermissionRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Permission permission = resolver(token, request);
        PermissionSearchDto searchDto = convertSearchDto(permission);
        Permission result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<PermissionSearchDto> find(PermissionRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Permission permission = resolver(token, request);
        PermissionSearchDto searchDto = convertSearchDto(permission);
        List<Permission> permissionList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return permissionList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<PermissionSearchDto> page(PermissionRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Permission permission = resolver(token, request);
        PermissionSearchDto searchDto = convertSearchDto(permission);
        IPage<Permission> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 多租户根据 {@code permissionIds} 获取 {@link Permission} 列表.
     *
     * @param tenantId      多租户 ID
     * @param permissionIds 权限 ID 列表
     * @return 权限列表, 只包含 {@code id, permissionCode, permissionUri, permissionVal} 字段
     */
    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<PermissionSearchDto> findByPermissionIds(Integer tenantId, List<Long> permissionIds) {
        Wrapper<Permission> permissionWrapper = Wrappers.lambdaQuery(Permission.class)
                                                        .select(Permission::getId,
                                                                Permission::getPermissionCode,
                                                                Permission::getPermissionUri,
                                                                Permission::getPermissionVal)
                                                        .eq(Permission::getTenantId, tenantId)
                                                        .in(Permission::getId, permissionIds)
                                                        .eq(Permission::getDeleted, Boolean.FALSE);
        List<Permission> permissionList = permissionService.findAllBySpec(permissionWrapper);
        return permissionList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

}
