package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.PostRequest;
import org.arch.ums.account.dto.PostSearchDto;
import org.arch.ums.account.entity.Post;
import org.arch.ums.account.rest.PostRest;
import org.arch.ums.account.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-岗位(Post) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostBiz implements CrudBiz<PostRequest, Post, java.lang.Long, PostSearchDto, PostSearchDto, PostService>, PostRest {

    private final TenantContextHolder tenantContextHolder;
    private final PostService postService;

    @Override
    public Post resolver(TokenInfo token, PostRequest request) {
        Post post = new Post();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, post);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            post.setTenantId(token.getTenantId());
        }
        else {
            post.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return post;
    }

    @Override
    public PostService getCrudService() {
        return postService;
    }

    @Override
    public PostSearchDto getSearchDto() {
        return new PostSearchDto();
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
    public PostSearchDto findOne(PostRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Post post = resolver(token, request);
        PostSearchDto searchDto = convertSearchDto(post);
        Post result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<PostSearchDto> find(PostRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Post post = resolver(token, request);
        PostSearchDto searchDto = convertSearchDto(post);
        List<Post> postList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return postList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<PostSearchDto> page(PostRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Post post = resolver(token, request);
        PostSearchDto searchDto = convertSearchDto(post);
        IPage<Post> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
