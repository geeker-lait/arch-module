package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dto.MemberRequest;
import org.arch.ums.account.dto.MemberSearchDto;
import org.arch.ums.account.entity.Member;
import org.arch.ums.account.service.MemberService;
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
 * 账号-会员账号(Member) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 21:59:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/member")
public class MemberController implements CrudController<MemberRequest, Member, java.lang.Long, MemberSearchDto, MemberService> {

    private final TenantContextHolder tenantContextHolder;
    private final MemberService memberService;

    @Override
    public Member resolver(TokenInfo token, MemberRequest request) {
        Member member = new Member();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, member);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            member.setTenantId(token.getTenantId());
        }
        else {
            member.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return member;
    }

    @Override
    public MemberService getCrudService() {
        return memberService;
    }

    @Override
    public MemberSearchDto getSearchDto() {
        return new MemberSearchDto();
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
    public Response<MemberSearchDto> findOne(@RequestBody @Valid MemberRequest request, TokenInfo token) {
        try {
            Member member = resolver(token, request);
            MemberSearchDto searchDto = convertSearchDto(member);
            Member result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<MemberSearchDto>> find(@RequestBody @Valid MemberRequest request, TokenInfo token) {
        Member member = resolver(token, request);
        MemberSearchDto searchDto = convertSearchDto(member);
        try {
            List<Member> memberList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(memberList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<MemberSearchDto>> page(@RequestBody @Valid MemberRequest request,
                                                 @PathVariable(value = "pageNumber") Integer pageNumber,
                                                 @PathVariable(value = "pageSize") Integer pageSize,
                                                 TokenInfo token) {
        Member member = resolver(token, request);
        MemberSearchDto searchDto = convertSearchDto(member);
        try {
            IPage<Member> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
