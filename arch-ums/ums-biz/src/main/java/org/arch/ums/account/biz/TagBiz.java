package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.arch.ums.account.entity.Tag;
import org.arch.ums.account.rest.TagRest;
import org.arch.ums.account.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-标签(Tag) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TagBiz implements CrudBiz<TagRequest, Tag, java.lang.Long, TagSearchDto, TagSearchDto, TagService>, TagRest {

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public TagSearchDto findOne(TagRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Tag tag = resolver(token, request);
        TagSearchDto searchDto = convertSearchDto(tag);
        Tag result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<TagSearchDto> find(TagRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Tag tag = resolver(token, request);
        TagSearchDto searchDto = convertSearchDto(tag);
        List<Tag> tagList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return tagList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<TagSearchDto> page(TagRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Tag tag = resolver(token, request);
        TagSearchDto searchDto = convertSearchDto(tag);
        IPage<Tag> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
