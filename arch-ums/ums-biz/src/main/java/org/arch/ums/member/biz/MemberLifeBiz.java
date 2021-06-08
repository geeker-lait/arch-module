package org.arch.ums.member.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.member.dto.MemberLifeRequest;
import org.arch.ums.member.dto.MemberLifeSearchDto;
import org.arch.ums.member.entity.MemberLife;
import org.arch.ums.member.rest.MemberLifeRest;
import org.arch.ums.member.service.MemberLifeService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 会员生命周期(MemberLife) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:59
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberLifeBiz implements CrudBiz<MemberLifeRequest, MemberLife, java.lang.Long, MemberLifeSearchDto, MemberLifeSearchDto, MemberLifeService>, MemberLifeRest {

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
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public MemberLifeSearchDto findOne(MemberLifeRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MemberLife memberLife = resolver(token, request);
        MemberLifeSearchDto searchDto = convertSearchDto(memberLife);
        MemberLife result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<MemberLifeSearchDto> find(MemberLifeRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MemberLife memberLife = resolver(token, request);
        MemberLifeSearchDto searchDto = convertSearchDto(memberLife);
        List<MemberLife> memberLifeList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return memberLifeList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<MemberLifeSearchDto> page(MemberLifeRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MemberLife memberLife = resolver(token, request);
        MemberLifeSearchDto searchDto = convertSearchDto(memberLife);
        IPage<MemberLife> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
