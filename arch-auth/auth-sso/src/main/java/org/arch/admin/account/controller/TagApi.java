package org.arch.admin.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountTagApi;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账号-标签(Tag) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:34:44
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminTagController")
@RequestMapping("/account/tag")
public class TagApi implements FeignCrudApi<TagSearchDto, Long, TagRequest, AccountTagApi> {

    private final AccountTagApi accountTagApi;

    @Override
    public AccountTagApi getApi() {
        return this.accountTagApi;
    }

}
