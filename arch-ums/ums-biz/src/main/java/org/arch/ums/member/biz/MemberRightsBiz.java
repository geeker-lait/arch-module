package org.arch.ums.member.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.member.dto.MemberRightsRequest;
import org.arch.ums.member.dto.MemberRightsSearchDto;
import org.arch.ums.member.entity.MemberRights;
import org.arch.ums.member.rest.MemberRightsRest;
import org.arch.ums.member.service.MemberRightsService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

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
    public MemberRightsSearchDto findOne(MemberRightsRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        MemberRights memberRights = resolver(token, request);
        MemberRightsSearchDto searchDto = convertSearchDto(memberRights);
        MemberRights result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<MemberRightsSearchDto> find(MemberRightsRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        MemberRights memberRights = resolver(token, request);
        MemberRightsSearchDto searchDto = convertSearchDto(memberRights);
        List<MemberRights> memberRightsList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return memberRightsList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<MemberRightsSearchDto> page(MemberRightsRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        MemberRights memberRights = resolver(token, request);
        MemberRightsSearchDto searchDto = convertSearchDto(memberRights);
        IPage<MemberRights> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
