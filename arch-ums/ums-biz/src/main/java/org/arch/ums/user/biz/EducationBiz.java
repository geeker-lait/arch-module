package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
import org.arch.ums.user.entity.Education;
import org.arch.ums.user.service.EducationService;
import org.arch.ums.user.rest.EducationRest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户学历信息(Education) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class EducationBiz implements CrudBiz<EducationRequest, Education, java.lang.Long, EducationSearchDto, EducationSearchDto, EducationService>, EducationRest {

    private final TenantContextHolder tenantContextHolder;
    private final EducationService educationService;

    @Override
    public Education resolver(TokenInfo token, EducationRequest request) {
        Education education = new Education();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, education);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            education.setTenantId(token.getTenantId());
        }
        else {
            education.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return education;
    }

    @Override
    public EducationService getCrudService() {
        return educationService;
    }

    @Override
    public EducationSearchDto getSearchDto() {
        return new EducationSearchDto();
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
    public EducationSearchDto findOne(EducationRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Education education = resolver(token, request);
        EducationSearchDto searchDto = convertSearchDto(education);
        Education result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<EducationSearchDto> find(EducationRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Education education = resolver(token, request);
        EducationSearchDto searchDto = convertSearchDto(education);
        List<Education> educationList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return educationList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<EducationSearchDto> page(EducationRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Education education = resolver(token, request);
        EducationSearchDto searchDto = convertSearchDto(education);
        IPage<Education> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
