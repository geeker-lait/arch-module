package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.account.dto.MemberSearchDto;
import org.arch.ums.account.entity.Member;
import org.arch.ums.account.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 账号-会员账号(Member) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:38:07
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/member")
public class MemberController implements CrudController<Member, Long, MemberSearchDto, MemberService> {

    private final AppProperties appProperties;
    private final MemberService memberService;

    @Override
    public Member resolver(TokenInfo token, Member member) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 member 后返回 member, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            member.setTenantId(token.getTenantId());
        }
        else {
            member.setTenantId(appProperties.getSystemTenantId());
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