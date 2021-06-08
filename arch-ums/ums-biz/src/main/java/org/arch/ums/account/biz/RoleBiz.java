package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.RoleRequest;
import org.arch.ums.account.dto.RoleSearchDto;
import org.arch.ums.account.entity.Role;
import org.arch.ums.account.rest.RoleRest;
import org.arch.ums.account.service.RoleService;
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
 * 账号-角色(Role) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:05
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleBiz implements CrudBiz<RoleRequest, Role, java.lang.Long, RoleSearchDto, RoleSearchDto, RoleService>, RoleRest {

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
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public RoleSearchDto findOne(RoleRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Role role = resolver(token, request);
        RoleSearchDto searchDto = convertSearchDto(role);
        Role result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<RoleSearchDto> find(RoleRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Role role = resolver(token, request);
        RoleSearchDto searchDto = convertSearchDto(role);
        List<Role> roleList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return roleList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<RoleSearchDto> page(RoleRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Role role = resolver(token, request);
        RoleSearchDto searchDto = convertSearchDto(role);
        IPage<Role> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 多租户根据 {@code roleIds} 获取 {@link Role} 列表.
     *
     * @param tenantId 多租户 ID
     * @param roleIds  角色 ID 列表
     * @return 角色列表
     */
    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<RoleSearchDto> findByMenuIds(Integer tenantId, List<Long> roleIds) {
        Wrapper<Role> roleWrapper = Wrappers.lambdaQuery(Role.class)
                                            .eq(Role::getTenantId, tenantId)
                                            .in(Role::getId, roleIds)
                                            .eq(Role::getDeleted, Boolean.FALSE);
        List<Role> roleList = roleService.findAllBySpec(roleWrapper);
        return roleList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

}
