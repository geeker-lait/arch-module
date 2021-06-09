package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dao.RolePermissionDao;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.dto.RolePermissionRequest;
import org.arch.ums.account.dto.RolePermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.arch.ums.account.entity.RolePermission;
import org.arch.ums.account.rest.RolePermissionRest;
import org.arch.ums.account.service.PermissionService;
import org.arch.ums.account.service.RolePermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * 账号-角色权限表(RolePermission) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:05
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RolePermissionBiz implements CrudBiz<RolePermissionRequest, RolePermission, java.lang.Long, RolePermissionSearchDto, RolePermissionSearchDto, RolePermissionService>, RolePermissionRest {

    private final TenantContextHolder tenantContextHolder;
    private final RolePermissionService rolePermissionService;
    private final AuthClientBiz authClientBiz;
    private final PermissionService permissionService;
    private final RolePermissionDao rolePermissionDao;

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
        return rolePermissionService;
    }

    @Override
    public RolePermissionSearchDto getSearchDto() {
        return new RolePermissionSearchDto();
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
    public RolePermissionSearchDto findOne(RolePermissionRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        RolePermission rolePermission = resolver(token, request);
        RolePermissionSearchDto searchDto = convertSearchDto(rolePermission);
        RolePermission result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<RolePermissionSearchDto> find(RolePermissionRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        RolePermission rolePermission = resolver(token, request);
        RolePermissionSearchDto searchDto = convertSearchDto(rolePermission);
        List<RolePermission> rolePermissionList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return rolePermissionList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<RolePermissionSearchDto> page(RolePermissionRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        RolePermission rolePermission = resolver(token, request);
        RolePermissionSearchDto searchDto = convertSearchDto(rolePermission);
        IPage<RolePermission> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 基于多租户, 更新角色 {@code roleId} 的权限资源
     *
     * @param tenantId          多租户 ID
     * @param roleId            角色 ID
     * @param permissionIds     权限资源 ids
     * @return 是否更新成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public Boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                   @NonNull Long roleId,
                                                   @NonNull List<Long> permissionIds) {

        List<RolePermission> rolePermissionList =
                permissionIds.stream()
                             .map(permissionId -> new RolePermission().setPermissionId(permissionId)
                                                                      .setRoleId(roleId)
                                                                      .setTenantId(tenantId.intValue())
                                                                      .setDeleted(FALSE))
                             .collect(toList());

        Wrapper<RolePermission> rolePermissionWrapper = Wrappers.lambdaQuery(RolePermission.class)
                                                                .eq(RolePermission::getTenantId, tenantId)
                                                                .eq(RolePermission::getRoleId, roleId)
                                                                .eq(RolePermission::getDeleted, FALSE);
        boolean removeResult = rolePermissionDao.remove(rolePermissionWrapper);
        if (!removeResult) {
            return false;
        }
        return rolePermissionDao.saveBatch(rolePermissionList);
    }

    /**
     * 基于 SCOPE, 更新角色 {@code roleId} 的 权限资源
     *
     * @param scopeId           SCOPE ID
     * @param roleId            角色 ID
     * @param permissionIds     权限资源 ids
     * @return 是否更新成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public Boolean updateResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    @NonNull List<Long> permissionIds) {
        String tenantId = tenantContextHolder.getTenantId();
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientBiz.hasRoleId(scopeId, roleId)) {
            return updateResourcesByRoleIdOfTenant(Long.valueOf(tenantId), roleId, permissionIds);
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
    public List<PermissionSearchDto> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId, @NonNull Long roleId) {
        // tenantId 与 roleId 都是 long 类型, 不需要担心 sql 注入
        String subQuerySql = "SELECT `permission_id` FROM `account_role_permission` " +
                "WHERE `tenant_id` = " + tenantId +
                " AND `role_id` = " + roleId +
                " AND `deleted` = 0";
        Wrapper<Permission> queryWrapper = Wrappers.lambdaQuery(Permission.class)
                                                   .eq(Permission::getTenantId, tenantId)
                                                   .eq(Permission::getDeleted, Boolean.FALSE)
                                                   .inSql(Permission::getId, subQuerySql);

        List<Permission> permissionList = this.permissionService.findAllBySpec(queryWrapper);
        return permissionList.stream()
                .map(permission -> {
                    PermissionSearchDto permissionSearchDto = new PermissionSearchDto();
                    BeanUtils.copyProperties(permission, permissionSearchDto);
                    return permissionSearchDto;
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
    public List<PermissionSearchDto> findAllResourcesByRoleIdOfScopeId(@NonNull Long scopeId, @NonNull Long roleId) {
        String tenantId = tenantContextHolder.getTenantId();
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientBiz.hasRoleId(scopeId, roleId)) {
            return findAllResourcesByRoleIdOfTenant(Long.valueOf(tenantId), roleId);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }

}
