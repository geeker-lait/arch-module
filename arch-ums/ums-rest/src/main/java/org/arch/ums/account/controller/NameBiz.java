package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dto.NameRequest;
import org.arch.ums.account.dto.NameSearchDto;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.service.NameService;
import org.arch.framework.crud.CrudBiz;
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
 * 账号名(Name) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:04:17
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/name")
public class NameBiz implements CrudBiz<NameRequest, Name, Long, NameSearchDto, NameService> {

    private final TenantContextHolder tenantContextHolder;
    private final NameService nameService;

    @Override
    public Name resolver(TokenInfo token, NameRequest request) {
        Name name = new Name();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, name);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            name.setTenantId(token.getTenantId());
        }
        else {
            name.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return name;
    }

    @Override
    public NameService getCrudService() {
        return nameService;
    }

    @Override
    public NameSearchDto getSearchDto() {
        return new NameSearchDto();
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 封装类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<NameSearchDto> findOne(@RequestBody @Valid NameRequest request, TokenInfo token) {
        try {
            Name name = resolver(token, request);
            NameSearchDto searchDto = convertSearchDto(name);
            Name result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
     * @param request 实体的 request 封装类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<NameSearchDto>> find(@RequestBody @Valid NameRequest request, TokenInfo token) {
        Name name = resolver(token, request);
        NameSearchDto searchDto = convertSearchDto(name);
        try {
            List<Name> nameList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(nameList.stream().map(this::convertReturnDto).collect(Collectors.toList()));
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
    public Response<IPage<NameSearchDto>> page(@RequestBody @Valid NameRequest request,
                                               @PathVariable(value = "pageNumber") Integer pageNumber,
                                               @PathVariable(value = "pageSize") Integer pageSize,
                                               TokenInfo token) {
        Name name = resolver(token, request);
        NameSearchDto searchDto = convertSearchDto(name);
        try {
            IPage<Name> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertReturnDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
