package org.arch.ums.member.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.member.dto.MemberRightsRequest;
import org.arch.ums.member.dto.MemberRightsSearchDto;
import org.arch.ums.member.entity.MemberRights;
import org.arch.ums.member.rest.MemberRightsRest;
import org.arch.ums.member.service.MemberRightsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 会员权益(MemberRights) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:59
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberRightsBiz implements CrudBiz<MemberRightsRequest, MemberRights, java.lang.Long, MemberRightsSearchDto, MemberRightsSearchDto, MemberRightsService>, MemberRightsRest {

    private final TenantContextHolder tenantContextHolder;
    private final MemberRightsService memberRightsService;

    @Override
    public MemberRights resolver(TokenInfo token, MemberRightsRequest request) {
        MemberRights memberRights = new MemberRights();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, memberRights);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            memberRights.setTenantId(token.getTenantId());
        }
        else {
            memberRights.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return memberRights;
    }

    @Override
    public MemberRightsService getCrudService() {
        return memberRightsService;
    }

    @Override
    public MemberRightsSearchDto getSearchDto() {
        return new MemberRightsSearchDto();
    }

}
