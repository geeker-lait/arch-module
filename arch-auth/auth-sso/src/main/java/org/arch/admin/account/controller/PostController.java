package org.arch.admin.account.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.account.dto.PostRequest;
import org.arch.ums.account.dto.PostSearchDto;
import org.arch.ums.account.client.AccountPostFeignService;
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
public class PostController implements FeignCrudController<PostSearchDto, java.lang.Long, PostRequest, AccountPostFeignService> {

    private final AccountPostFeignService accountPostService;

    @Override
    public AccountPostFeignService getFeignService() {
        return this.accountPostService;
    }

}
