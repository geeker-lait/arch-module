package org.arch.ums.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.member.dto.MemberLifeRequest;
import org.arch.ums.member.dto.MemberLifeSearchDto;
import org.arch.ums.member.entity.MemberLife;
import org.arch.ums.member.service.MemberLifeService;
import org.arch.framework.crud.CrudBiz;
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
 * 会员生命周期(MemberLife) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:48:49
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member/life")
public class MemberLifeBiz implements CrudBiz<MemberLifeRequest, MemberLife, Long, MemberLifeSearchDto, MemberLifeService> {

    private final TenantContextHolder tenantContextHolder;
    private final MemberLifeService memberLifeService;

    @Override
    public MemberLife resolver(TokenInfo token, MemberLifeRequest request) {
        MemberLife memberLife = new MemberLife();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, memberLife);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            memberLife.setTenantId(token.getTenantId());
        }
        else {
            memberLife.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return memberLife;
    }

    @Override
    public MemberLifeService getCrudService() {
        return memberLifeService;
    }

    @Override
    public MemberLifeSearchDto getSearchDto() {
        return new MemberLifeSearchDto();
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
    public Response<MemberLifeSearchDto> findOne(@RequestBody @Valid MemberLifeRequest request, TokenInfo token) {
        try {
            MemberLife memberLife = resolver(token, request);
            MemberLifeSearchDto searchDto = convertSearchDto(memberLife);
            MemberLife result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<MemberLifeSearchDto>> find(@RequestBody @Valid MemberLifeRequest request, TokenInfo token) {
        MemberLife memberLife = resolver(token, request);
        MemberLifeSearchDto searchDto = convertSearchDto(memberLife);
        try {
            List<MemberLife> memberLifeList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(memberLifeList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
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
    public Response<IPage<MemberLifeSearchDto>> page(@RequestBody @Valid MemberLifeRequest request,
                                                     @PathVariable(value = "pageNumber") Integer pageNumber,
                                                     @PathVariable(value = "pageSize") Integer pageSize,
                                                     TokenInfo token) {
        MemberLife memberLife = resolver(token, request);
        MemberLifeSearchDto searchDto = convertSearchDto(memberLife);
        try {
            IPage<MemberLife> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertReturnDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
