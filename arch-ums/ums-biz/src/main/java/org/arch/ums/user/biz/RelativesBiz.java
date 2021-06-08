package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.RelativesRequest;
import org.arch.ums.user.dto.RelativesSearchDto;
import org.arch.ums.user.entity.Relatives;
import org.arch.ums.user.service.RelativesService;
import org.arch.ums.user.rest.RelativesRest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户亲朋信息(Relatives) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class RelativesBiz implements CrudBiz<RelativesRequest, Relatives, java.lang.Long, RelativesSearchDto, RelativesSearchDto, RelativesService>, RelativesRest {

    private final TenantContextHolder tenantContextHolder;
    private final RelativesService relativesService;

    @Override
    public Relatives resolver(TokenInfo token, RelativesRequest request) {
        Relatives relatives = new Relatives();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, relatives);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            relatives.setTenantId(token.getTenantId());
        }
        else {
            relatives.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return relatives;
    }

    @Override
    public RelativesService getCrudService() {
        return relativesService;
    }

    @Override
    public RelativesSearchDto getSearchDto() {
        return new RelativesSearchDto();
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
    public RelativesSearchDto findOne(RelativesRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relatives relatives = resolver(token, request);
        RelativesSearchDto searchDto = convertSearchDto(relatives);
        Relatives result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<RelativesSearchDto> find(RelativesRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relatives relatives = resolver(token, request);
        RelativesSearchDto searchDto = convertSearchDto(relatives);
        List<Relatives> relativesList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return relativesList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<RelativesSearchDto> page(RelativesRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relatives relatives = resolver(token, request);
        RelativesSearchDto searchDto = convertSearchDto(relatives);
        IPage<Relatives> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
