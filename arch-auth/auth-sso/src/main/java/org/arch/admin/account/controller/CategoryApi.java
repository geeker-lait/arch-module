package org.arch.admin.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountCategoryApi;
import org.arch.ums.account.dto.CategoryRequest;
import org.arch.ums.account.dto.CategorySearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账号-资源类目(Category) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:36:13
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminCategoryController")
@RequestMapping("/account/category")
public class CategoryApi implements FeignCrudApi<CategorySearchDto, Long, CategoryRequest, AccountCategoryApi> {

    private final AccountCategoryApi accountCategoryApi;

    @Override
    public AccountCategoryApi getApi() {
        return this.accountCategoryApi;
    }

}
