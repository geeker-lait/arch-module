package org.arch.admin.account.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.arch.ums.account.client.AccountTagFeignService;
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
public class TagController implements FeignCrudController<TagSearchDto, java.lang.Long, TagRequest, AccountTagFeignService> {

    private final AccountTagFeignService accountTagService;

    @Override
    public AccountTagFeignService getFeignService() {
        return this.accountTagService;
    }

}
