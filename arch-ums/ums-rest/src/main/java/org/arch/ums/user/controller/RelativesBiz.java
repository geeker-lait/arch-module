package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.ums.user.dto.RelativesRequest;
import org.arch.ums.user.dto.RelativesSearchDto;
import org.arch.ums.user.entity.Relatives;
import org.arch.ums.user.service.RelativesService;
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
 * 用户亲朋信息(Relatives) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 23:08:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/relatives")
public class RelativesBiz implements CrudBiz<RelativesRequest, Relatives, Long, RelativesSearchDto, RelativesService> {

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
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<RelativesSearchDto> findOne(@RequestBody @Valid RelativesRequest request, TokenInfo token) {
        try {
            Relatives relatives = resolver(token, request);
            RelativesSearchDto searchDto = convertSearchDto(relatives);
            Relatives result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertReturnDto(result));
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
    public Response<List<RelativesSearchDto>> find(@RequestBody @Valid RelativesRequest request, TokenInfo token) {
        Relatives relatives = resolver(token, request);
        RelativesSearchDto searchDto = convertSearchDto(relatives);
        try {
            List<Relatives> relativesList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(relativesList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
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
    public Response<IPage<RelativesSearchDto>> page(@RequestBody @Valid RelativesRequest request,
                                                    @PathVariable(value = "pageNumber") Integer pageNumber,
                                                    @PathVariable(value = "pageSize") Integer pageSize,
                                                    TokenInfo token) {
        Relatives relatives = resolver(token, request);
        RelativesSearchDto searchDto = convertSearchDto(relatives);
        try {
            IPage<Relatives> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertReturnDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
