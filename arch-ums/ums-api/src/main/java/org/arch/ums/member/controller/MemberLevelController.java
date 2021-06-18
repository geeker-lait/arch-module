package org.arch.ums.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.member.dto.MemberLevelRequest;
import org.arch.ums.member.dto.MemberLevelSearchDto;
import org.arch.ums.member.entity.MemberLevel;
import org.arch.ums.member.service.MemberLevelService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.beans.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 会员级别(MemberLevel) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:48:48
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member/level")
public class MemberLevelController implements CrudController<MemberLevelRequest, MemberLevel, java.lang.Long, MemberLevelSearchDto, MemberLevelService> {

    private final TenantContextHolder tenantContextHolder;
    private final MemberLevelService memberLevelService;

    @Override
    public MemberLevel resolver(TokenInfo token, MemberLevelRequest request) {
        MemberLevel memberLevel = new MemberLevel();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, memberLevel);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            memberLevel.setTenantId(token.getTenantId());
        }
        else {
            memberLevel.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return memberLevel;
    }

    @Override
    public MemberLevelService getCrudService() {
        return memberLevelService;
    }

    @Override
    public MemberLevelSearchDto getSearchDto() {
        return new MemberLevelSearchDto();
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
    public Response<MemberLevelSearchDto> findOne(@RequestBody @Valid MemberLevelRequest request, TokenInfo token) {
        try {
            MemberLevel memberLevel = resolver(token, request);
            MemberLevelSearchDto searchDto = convertSearchDto(memberLevel);
            MemberLevel result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<MemberLevelSearchDto>> find(@RequestBody @Valid MemberLevelRequest request, TokenInfo token) {
        MemberLevel memberLevel = resolver(token, request);
        MemberLevelSearchDto searchDto = convertSearchDto(memberLevel);
        try {
            List<MemberLevel> memberLevelList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(memberLevelList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<MemberLevelSearchDto>> page(@RequestBody @Valid MemberLevelRequest request,
                                                      @PathVariable(value = "pageNumber") Integer pageNumber,
                                                      @PathVariable(value = "pageSize") Integer pageSize,
                                                      TokenInfo token) {
        MemberLevel memberLevel = resolver(token, request);
        MemberLevelSearchDto searchDto = convertSearchDto(memberLevel);
        try {
            IPage<MemberLevel> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
