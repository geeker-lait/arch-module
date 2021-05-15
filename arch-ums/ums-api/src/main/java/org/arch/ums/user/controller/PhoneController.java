package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.dto.PhoneRequest;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.service.PhoneService;
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
 * 用户电话信息(Phone) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 23:08:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/phone")
public class PhoneController implements CrudController<PhoneRequest, Phone, java.lang.Long, PhoneSearchDto, PhoneService> {

    private final TenantContextHolder tenantContextHolder;
    private final PhoneService phoneService;

    @Override
    public Phone resolver(TokenInfo token, PhoneRequest request) {
        Phone phone = new Phone();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, phone);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            phone.setTenantId(token.getTenantId());
        }
        else {
            phone.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return phone;
    }

    @Override
    public PhoneService getCrudService() {
        return phoneService;
    }

    @Override
    public PhoneSearchDto getSearchDto() {
        return new PhoneSearchDto();
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
    public Response<PhoneSearchDto> findOne(@RequestBody @Valid PhoneRequest request, TokenInfo token) {
        try {
            Phone phone = resolver(token, request);
            PhoneSearchDto searchDto = convertSearchDto(phone);
            Phone result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<PhoneSearchDto>> find(@RequestBody @Valid PhoneRequest request, TokenInfo token) {
        Phone phone = resolver(token, request);
        PhoneSearchDto searchDto = convertSearchDto(phone);
        try {
            List<Phone> phoneList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(phoneList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<PhoneSearchDto>> page(@RequestBody @Valid PhoneRequest request,
                                                @PathVariable(value = "pageNumber") Integer pageNumber,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                TokenInfo token) {
        Phone phone = resolver(token, request);
        PhoneSearchDto searchDto = convertSearchDto(phone);
        try {
            IPage<Phone> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
