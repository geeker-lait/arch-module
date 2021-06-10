package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.MemberRequest;
import org.arch.ums.account.dto.MemberSearchDto;
import org.arch.ums.account.entity.Member;
import org.arch.ums.account.rest.MemberRest;
import org.arch.ums.account.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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

}
