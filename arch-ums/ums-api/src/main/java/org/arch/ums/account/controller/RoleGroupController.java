package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RoleGroupSearchDto;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.account.service.RoleGroupService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-角色组织或机构(RoleGroup) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-03-01 00:22:30
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/group")
public class RoleGroupController implements CrudController<RoleGroup, java.lang.Long, RoleGroupSearchDto, RoleGroupService> {

    private final TenantContextHolder tenantContextHolder;
    private final RoleGroupService roleGroupService;

    @Override
    public RoleGroup resolver(TokenInfo token, RoleGroup roleGroup) {
        if (isNull(roleGroup)) {
            roleGroup = new RoleGroup();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            roleGroup.setTenantId(token.getTenantId());
        }
        else {
            roleGroup.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return roleGroup;
    }

    @Override
    public RoleGroupService getCrudService() {
        return roleGroupService;
    }

    @Override
    public RoleGroupSearchDto getSearchDto() {
        return new RoleGroupSearchDto();
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param entity 实体类
     * @param token  token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<RoleGroup> findOne(@RequestBody RoleGroup entity, TokenInfo token) {
        try {
            resolver(token, entity);
            RoleGroupSearchDto searchDto = convertSearchDto(entity);
            RoleGroup t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
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
     * @param t     实体类
     * @param token token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<RoleGroup>> find(@RequestBody RoleGroup t, TokenInfo token) {
        resolver(token, t);
        RoleGroupSearchDto searchDto = convertSearchDto(t);
        try {
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
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
     * @param entity     实体类
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<RoleGroup>> page(@RequestBody RoleGroup entity,
                                           @PathVariable(value = "pageNumber") Integer pageNumber,
                                           @PathVariable(value = "pageSize") Integer pageSize,
                                           TokenInfo token) {
        resolver(token, entity);
        RoleGroupSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 获取所有租户的所有角色组的角色
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @GetMapping("/listGroups")
    @NonNull
    public Response<Map<String, Map<String, Set<String>>>> listAllGroups() {
        try {
            return Response.success(this.roleGroupService.listAllGroups());
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 多租户获取 所有 group 的所有角色资源
     *
     * @param tenantId 多租户 ID
     * @param groupId  用户的 groupId
     * @param roleIds  用户的角色 ids
     * @return  Map(tenantAuthority, Map(groupAuthority, Set(roleAuthority))), 如果不存在这返回空集合.
     */
    @GetMapping("/find/{tenantId:\\d+}/{groupId:\\d+}")
    @NonNull
    public Response<Map<String, Map<String, Set<String>>>> findGroupRolesByGroupIdOfTenant(
                                                                    @PathVariable(value = "tenantId") Integer tenantId,
                                                                    @PathVariable(value = "groupId") Long groupId,
                                                                    @RequestBody List<Long> roleIds) {
        try {
            return Response.success(this.roleGroupService.findGroupRolesByGroupIdOfTenant(tenantId, groupId, roleIds));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 基于多租户, 查询指定角色组 {@code groupId} 所拥有的所有角色集合, Set(roleAuthority).
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @return groupId 所拥有的所有角色集合, Set(roleAuthority).
     */
    @GetMapping("/findRoles/{tenantId}/{groupId}")
    @NonNull
    public Response<Set<String>> findRolesByGroupIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                            @PathVariable(value = "groupId") Long groupId) {
        try {
            return Response.success(this.roleGroupService.findRolesByGroupIdOfTenant(tenantId, groupId));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 基于多租户, 更新角色组 {@code groupId} 的角色集合
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @param roleIds  角色 ids
     * @return 是否更新成功
     */
    @PutMapping("/updateRoles/{tenantId}/{groupId}")
    @NonNull
    public Response<Boolean> updateRolesByGroupIdOfTenant(@PathVariable(value = "tenantId") Long tenantId,
                                                          @PathVariable(value = "groupId") Long groupId,
                                                          @RequestBody List<Long> roleIds,
                                                          TokenInfo token) {
        // 必须是登录用户, 且 tenantId 必须与登录用户的 tenantId 相同
        if (isNull(token) || !(token.getTenantId().equals(tenantId.intValue()))) {
            Response.failed(AuthStatusCode.FORBIDDEN);
        }
        try {
            return Response.success(this.roleGroupService.updateRolesByGroupIdOfTenant(tenantId, groupId, roleIds));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
