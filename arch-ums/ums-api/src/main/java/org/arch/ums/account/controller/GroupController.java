package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.GroupRequest;
import org.arch.ums.account.dto.GroupSearchDto;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.service.GroupService;
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
 * 账号-组织机构(Group) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 21:39:20
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/group")
public class GroupController implements CrudController<GroupRequest, Group, java.lang.Long, GroupSearchDto, GroupService> {

    private final TenantContextHolder tenantContextHolder;
    private final GroupService groupService;

    @Override
    public Group resolver(TokenInfo token, GroupRequest request) {
        Group group = new Group();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, group);
        }
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
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<GroupSearchDto> findOne(@RequestBody @Valid GroupRequest request, TokenInfo token) {
        try {
            Group group = resolver(token, request);
            GroupSearchDto searchDto = convertSearchDto(group);
            Group result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<GroupSearchDto>> find(@RequestBody @Valid GroupRequest request, TokenInfo token) {
        Group group = resolver(token, request);
        GroupSearchDto searchDto = convertSearchDto(group);
        try {
            List<Group> groupList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(groupList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<GroupSearchDto>> page(@RequestBody @Valid GroupRequest request,
                                                @PathVariable(value = "pageNumber") Integer pageNumber,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                TokenInfo token) {
        Group group = resolver(token, request);
        GroupSearchDto searchDto = convertSearchDto(group);
        try {
            IPage<Group> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
