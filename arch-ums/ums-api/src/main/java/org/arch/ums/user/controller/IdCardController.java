package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.dto.IdCardRequest;
import org.arch.ums.user.dto.IdCardSearchDto;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.service.IdCardService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.beans.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 用户身份证表(IdCard) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 23:08:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/id/card")
public class IdCardController implements CrudController<IdCardRequest, IdCard, java.lang.Long, IdCardSearchDto, IdCardService> {

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
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<IdCardSearchDto> findOne(@RequestBody @Valid IdCardRequest request, TokenInfo token) {
        try {
            IdCard idCard = resolver(token, request);
            IdCardSearchDto searchDto = convertSearchDto(idCard);
            IdCard result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertSearchDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<IdCardSearchDto>> find(@RequestBody @Valid IdCardRequest request, TokenInfo token) {
        IdCard idCard = resolver(token, request);
        IdCardSearchDto searchDto = convertSearchDto(idCard);
        try {
            List<IdCard> idCardList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(idCardList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<IdCardSearchDto>> page(@RequestBody @Valid IdCardRequest request,
                                                 @PathVariable(value = "pageNumber") Integer pageNumber,
                                                 @PathVariable(value = "pageSize") Integer pageSize,
                                                 TokenInfo token) {
        IdCard idCard = resolver(token, request);
        IdCardSearchDto searchDto = convertSearchDto(idCard);
        try {
            IPage<IdCard> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
