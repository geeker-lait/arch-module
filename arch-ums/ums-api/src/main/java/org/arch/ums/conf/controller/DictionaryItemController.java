package org.arch.ums.conf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.arch.ums.conf.entity.DictionaryItem;
import org.arch.ums.conf.service.DictionaryItemService;
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
 * 数据字典明细(DictionaryItem) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:31:44
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/dictionary/item")
public class DictionaryItemController implements CrudController<DictionaryItemRequest, DictionaryItem, java.lang.Long, DictionaryItemSearchDto, DictionaryItemService> {

    private final TenantContextHolder tenantContextHolder;
    private final DictionaryItemService dictionaryItemService;

    @Override
    public DictionaryItem resolver(TokenInfo token, DictionaryItemRequest request) {
        DictionaryItem dictionaryItem = new DictionaryItem();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, dictionaryItem);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            dictionaryItem.setTenantId(token.getTenantId());
        }
        else {
            dictionaryItem.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return dictionaryItem;
    }

    @Override
    public DictionaryItemService getCrudService() {
        return dictionaryItemService;
    }

    @Override
    public DictionaryItemSearchDto getSearchDto() {
        return new DictionaryItemSearchDto();
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
    public Response<DictionaryItemSearchDto> findOne(@RequestBody @Valid DictionaryItemRequest request, TokenInfo token) {
        try {
            DictionaryItem dictionaryItem = resolver(token, request);
            DictionaryItemSearchDto searchDto = convertSearchDto(dictionaryItem);
            DictionaryItem result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<DictionaryItemSearchDto>> find(@RequestBody @Valid DictionaryItemRequest request, TokenInfo token) {
        DictionaryItem dictionaryItem = resolver(token, request);
        DictionaryItemSearchDto searchDto = convertSearchDto(dictionaryItem);
        try {
            List<DictionaryItem> dictionaryItemList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(dictionaryItemList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<DictionaryItemSearchDto>> page(@RequestBody @Valid DictionaryItemRequest request,
                                                         @PathVariable(value = "pageNumber") Integer pageNumber,
                                                         @PathVariable(value = "pageSize") Integer pageSize,
                                                         TokenInfo token) {
        DictionaryItem dictionaryItem = resolver(token, request);
        DictionaryItemSearchDto searchDto = convertSearchDto(dictionaryItem);
        try {
            IPage<DictionaryItem> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
