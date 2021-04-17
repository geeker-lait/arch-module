package org.arch.ums.conf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.arch.ums.conf.entity.DictionaryItem;
import org.arch.ums.conf.service.DictionaryItemService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 数据字典明细(DictionaryItem) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:24:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/dictionary/item")
public class DictionaryItemController implements CrudController<DictionaryItem, java.lang.Long, DictionaryItemSearchDto, DictionaryItemService> {

    private final TenantContextHolder tenantContextHolder;
    private final DictionaryItemService dictionaryItemService;

    @Override
    public DictionaryItem resolver(TokenInfo token, DictionaryItem dictionaryItem) {
        if (isNull(dictionaryItem)) {
            dictionaryItem = new DictionaryItem();
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
     * @param entity 实体类
     * @param token  token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<DictionaryItem> findOne(@RequestBody DictionaryItem entity, TokenInfo token) {
        try {
            resolver(token, entity);
            DictionaryItemSearchDto searchDto = convertSearchDto(entity);
            DictionaryItem t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
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
     * @param t     实体类
     * @param token token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<DictionaryItem>> find(@RequestBody DictionaryItem t, TokenInfo token) {
        resolver(token, t);
        DictionaryItemSearchDto searchDto = convertSearchDto(t);
        try {
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
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
     * @param entity     实体类
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<DictionaryItem>> page(@RequestBody DictionaryItem entity,
                                                @PathVariable(value = "pageNumber") Integer pageNumber,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                TokenInfo token) {
        resolver(token, entity);
        DictionaryItemSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
