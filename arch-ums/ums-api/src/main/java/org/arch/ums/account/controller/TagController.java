package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.arch.ums.account.entity.Tag;
import org.arch.ums.account.service.TagService;
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
 * 账号-标签(Tag) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:14:54
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/tag")
public class TagController implements CrudController<TagRequest, Tag, java.lang.Long, TagSearchDto, TagService> {

    private final TenantContextHolder tenantContextHolder;
    private final TagService tagService;

    @Override
    public Tag resolver(TokenInfo token, TagRequest request) {
        Tag tag = new Tag();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, tag);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            tag.setTenantId(token.getTenantId());
        }
        else {
            tag.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return tag;
    }

    @Override
    public TagService getCrudService() {
        return tagService;
    }

    @Override
    public TagSearchDto getSearchDto() {
        return new TagSearchDto();
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
    public Response<TagSearchDto> findOne(@RequestBody @Valid TagRequest request, TokenInfo token) {
        try {
            Tag tag = resolver(token, request);
            TagSearchDto searchDto = convertSearchDto(tag);
            Tag result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<TagSearchDto>> find(@RequestBody @Valid TagRequest request, TokenInfo token) {
        Tag tag = resolver(token, request);
        TagSearchDto searchDto = convertSearchDto(tag);
        try {
            List<Tag> tagList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(tagList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<TagSearchDto>> page(@RequestBody @Valid TagRequest request,
                                              @PathVariable(value = "pageNumber") Integer pageNumber,
                                              @PathVariable(value = "pageSize") Integer pageSize,
                                              TokenInfo token) {
        Tag tag = resolver(token, request);
        TagSearchDto searchDto = convertSearchDto(tag);
        try {
            IPage<Tag> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
