package org.arch.ums.conf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.arch.ums.conf.entity.Dictionary;
import org.arch.ums.conf.service.DictionaryService;
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
 * 数据字典(Dictionary) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:31:44
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/dictionary")
public class DictionaryController implements CrudController<DictionaryRequest, Dictionary, java.lang.Long, DictionarySearchDto, DictionaryService> {

    private final TenantContextHolder tenantContextHolder;
    private final DictionaryService dictionaryService;

    @Override
    public Dictionary resolver(TokenInfo token, DictionaryRequest request) {
        Dictionary dictionary = new Dictionary();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, dictionary);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            dictionary.setTenantId(token.getTenantId());
        }
        else {
            dictionary.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return dictionary;
    }

    @Override
    public DictionaryService getCrudService() {
        return dictionaryService;
    }

    @Override
    public DictionarySearchDto getSearchDto() {
        return new DictionarySearchDto();
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
    public Response<DictionarySearchDto> findOne(@RequestBody @Valid DictionaryRequest request, TokenInfo token) {
        try {
            Dictionary dictionary = resolver(token, request);
            DictionarySearchDto searchDto = convertSearchDto(dictionary);
            Dictionary result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<DictionarySearchDto>> find(@RequestBody @Valid DictionaryRequest request, TokenInfo token) {
        Dictionary dictionary = resolver(token, request);
        DictionarySearchDto searchDto = convertSearchDto(dictionary);
        try {
            List<Dictionary> dictionaryList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(dictionaryList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<DictionarySearchDto>> page(@RequestBody @Valid DictionaryRequest request,
                                                     @PathVariable(value = "pageNumber") Integer pageNumber,
                                                     @PathVariable(value = "pageSize") Integer pageSize,
                                                     TokenInfo token) {
        Dictionary dictionary = resolver(token, request);
        DictionarySearchDto searchDto = convertSearchDto(dictionary);
        try {
            IPage<Dictionary> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
