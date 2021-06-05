package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RelationshipRequest;
import org.arch.ums.account.dto.RelationshipSearchDto;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.service.RelationshipService;
import org.arch.ums.biz.account.service.BizRelationshipService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 * 账号-关系(Relationship) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:10:53
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/relationship")
public class RelationshipBiz implements CrudBiz<RelationshipRequest, Relationship, Long, RelationshipSearchDto, RelationshipService> {

    private final TenantContextHolder tenantContextHolder;
    private final BizRelationshipService bizRelationshipService;

    @Override
    public Relationship resolver(TokenInfo token, RelationshipRequest request) {
        Relationship relationship = new Relationship();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, relationship);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            relationship.setTenantId(token.getTenantId());
        }
        else {
            relationship.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return relationship;
    }

    @Override
    public RelationshipService getCrudService() {
        return bizRelationshipService;
    }

    @Override
    public RelationshipSearchDto getSearchDto() {
        return new RelationshipSearchDto();
    }

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param request      实体的 request 封装类型
     * @param token        token info
     * @return  {@link Response}
     */
    @Override
    @PostMapping
    public Response<RelationshipSearchDto> save(@Valid @RequestBody RelationshipRequest request, TokenInfo token) {
        Relationship relationship = resolver(token, request);
        if (isNull(request.getSeq()) || isNull(request.getOrg())) {
            bizRelationshipService.saveMax(relationship);
        }
        else {
            bizRelationshipService.save(relationship);
        }
        return Response.success(convertReturnDto(relationship));
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
    public Response<RelationshipSearchDto> findOne(@RequestBody @Valid RelationshipRequest request, TokenInfo token) {
        try {
            Relationship relationship = resolver(token, request);
            RelationshipSearchDto searchDto = convertSearchDto(relationship);
            Relationship result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<RelationshipSearchDto>> find(@RequestBody @Valid RelationshipRequest request, TokenInfo token) {
        Relationship relationship = resolver(token, request);
        RelationshipSearchDto searchDto = convertSearchDto(relationship);
        try {
            List<Relationship> relationshipList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(relationshipList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
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
    public Response<IPage<RelationshipSearchDto>> page(@RequestBody @Valid RelationshipRequest request,
                                                       @PathVariable(value = "pageNumber") Integer pageNumber,
                                                       @PathVariable(value = "pageSize") Integer pageSize,
                                                       TokenInfo token) {
        Relationship relationship = resolver(token, request);
        RelationshipSearchDto searchDto = convertSearchDto(relationship);
        try {
            IPage<Relationship> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertReturnDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
