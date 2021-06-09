package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.MemberRequest;
import org.arch.ums.account.dto.MemberSearchDto;
import org.arch.ums.account.entity.Member;
import org.arch.ums.account.rest.MemberRest;
import org.arch.ums.account.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-会员账号(Member) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:02
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberBiz implements CrudBiz<MemberRequest, Member, java.lang.Long, MemberSearchDto, MemberSearchDto, MemberService>, MemberRest {

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public MemberSearchDto findOne(MemberRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Member member = resolver(token, request);
        MemberSearchDto searchDto = convertSearchDto(member);
        Member result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<MemberSearchDto> find(MemberRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Member member = resolver(token, request);
        MemberSearchDto searchDto = convertSearchDto(member);
        List<Member> memberList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return memberList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<MemberSearchDto> page(MemberRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Member member = resolver(token, request);
        MemberSearchDto searchDto = convertSearchDto(member);
        IPage<Member> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
