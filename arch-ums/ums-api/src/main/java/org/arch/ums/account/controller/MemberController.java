package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.MemberSearchDto;
import org.arch.ums.account.entity.Member;
import org.arch.ums.account.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-会员账号(Member) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:12:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/member")
public class MemberController implements CrudController<Member, java.lang.Long, MemberSearchDto, MemberService> {

    private final TenantContextHolder tenantContextHolder;
    private final MemberService memberService;

    @Override
    public Member resolver(TokenInfo token, Member member) {
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
