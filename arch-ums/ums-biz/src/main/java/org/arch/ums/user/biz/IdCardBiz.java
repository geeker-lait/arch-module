package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.IdCardRequest;
import org.arch.ums.user.dto.IdCardSearchDto;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.service.IdCardService;
import org.arch.ums.user.rest.IdCardRest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户身份证表(IdCard) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class IdCardBiz implements CrudBiz<IdCardRequest, IdCard, java.lang.Long, IdCardSearchDto, IdCardSearchDto, IdCardService>, IdCardRest {

    private final TenantContextHolder tenantContextHolder;
    private final IdCardService idCardService;

    @Override
    public IdCard resolver(TokenInfo token, IdCardRequest request) {
        IdCard idCard = new IdCard();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, idCard);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            idCard.setTenantId(token.getTenantId());
        }
        else {
            idCard.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return idCard;
    }

    @Override
    public IdCardService getCrudService() {
        return idCardService;
    }

    @Override
    public IdCardSearchDto getSearchDto() {
        return new IdCardSearchDto();
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
    public IdCardSearchDto findOne(IdCardRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        IdCard idCard = resolver(token, request);
        IdCardSearchDto searchDto = convertSearchDto(idCard);
        IdCard result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<IdCardSearchDto> find(IdCardRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        IdCard idCard = resolver(token, request);
        IdCardSearchDto searchDto = convertSearchDto(idCard);
        List<IdCard> idCardList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return idCardList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<IdCardSearchDto> page(IdCardRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        IdCard idCard = resolver(token, request);
        IdCardSearchDto searchDto = convertSearchDto(idCard);
        IPage<IdCard> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
