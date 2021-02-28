package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.PostSearchDto;
import org.arch.ums.account.entity.Post;
import org.arch.ums.account.service.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-岗位(Post) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:16:07
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/post")
public class PostController implements CrudController<Post, java.lang.Long, PostSearchDto, PostService> {

    private final TenantContextHolder tenantContextHolder;
    private final PostService postService;

    @Override
    public Post resolver(TokenInfo token, Post post) {
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
