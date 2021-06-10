package org.arch.ums.member.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.member.dto.MemberLevelRequest;
import org.arch.ums.member.dto.MemberLevelSearchDto;
import org.arch.ums.member.entity.MemberLevel;
import org.arch.ums.member.rest.MemberLevelRest;
import org.arch.ums.member.service.MemberLevelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 会员级别(MemberLevel) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:59
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberLevelBiz implements CrudBiz<MemberLevelRequest, MemberLevel, java.lang.Long, MemberLevelSearchDto, MemberLevelSearchDto, MemberLevelService>, MemberLevelRest {

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

}
