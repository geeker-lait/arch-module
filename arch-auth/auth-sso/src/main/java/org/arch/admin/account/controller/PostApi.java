package org.arch.admin.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountPostApi;
import org.arch.ums.account.dto.PostRequest;
import org.arch.ums.account.dto.PostSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账号-岗位(Post) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:36:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminPostController")
@RequestMapping("/account/post")
public class PostApi implements FeignCrudApi<PostSearchDto, Long, PostRequest, AccountPostApi> {

    private final AccountPostApi accountPostApi;

    @Override
    public AccountPostApi getFeignApi() {
        return this.accountPostApi;
    }

}
