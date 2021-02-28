package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.GroupSearchDto;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.service.GroupService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-组织机构(Group) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:37:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/group")
public class GroupController implements CrudController<Group, Long, GroupSearchDto, GroupService> {

    private final TenantContextHolder tenantContextHolder;
    private final GroupService groupService;

    @Override
    public Group resolver(TokenInfo token, Group group) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            group.setTenantId(token.getTenantId());
        }
        else {
            group.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return group;
    }

    @Override
    public GroupService getCrudService() {
        return groupService;
    }

    @Override
    public GroupSearchDto getSearchDto() {
        return new GroupSearchDto();
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     * @param entity    实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @Override
    @GetMapping("/single")
    public Response<Group> findOne(@RequestBody Group entity, TokenInfo token) {
        try {
            resolver(token, entity);
            GroupSearchDto searchDto = convertSearchDto(entity);
            Group t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
        } catch (Exception e) {
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     * @param t         实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @Override
    @GetMapping("/find")
    public Response<List<Group>> find(@RequestBody Group t, TokenInfo token) {
        resolver(token, t);
        GroupSearchDto searchDto = convertSearchDto(t);
        return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     * @param entity        实体类
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @param token         token info
     * @return  {@link Response}
     */
    @Override
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<Group>> page(@RequestBody Group entity,
                                          @PathVariable(value = "pageNumber") Integer pageNumber,
                                          @PathVariable(value = "pageSize") Integer pageSize,
                                          TokenInfo token) {
        resolver(token, entity);
        GroupSearchDto searchDto = convertSearchDto(entity);
        return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
    }
}