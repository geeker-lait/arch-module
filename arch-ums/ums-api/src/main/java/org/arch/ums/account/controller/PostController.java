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
 * @date 2021-01-30 11:39:33
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/post")
public class PostController implements CrudController<Post, Long, PostSearchDto, PostService> {

    private final PostService postService;
    private final TenantContextHolder tenantContextHolder;

    @Override
    public Post resolver(TokenInfo token, Post post) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 post 后返回 post, 如: tenantId 的处理等.
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