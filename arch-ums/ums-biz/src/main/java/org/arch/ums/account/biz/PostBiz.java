package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.PostRequest;
import org.arch.ums.account.dto.PostSearchDto;
import org.arch.ums.account.entity.Post;
import org.arch.ums.account.rest.PostRest;
import org.arch.ums.account.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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

}
