package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.CategorySearchDto;
import org.arch.ums.account.entity.Category;
import org.arch.ums.account.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-资源类目(Category) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:49:20
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/category")
public class CategoryController implements CrudController<Category, java.lang.Long, CategorySearchDto, CategoryService> {

    private final CategoryService categoryService;

    @Override
    public Category resolver(TokenInfo token, Category category) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 category 后返回 category, 如: tenantId 的处理等.
        return category;
    }

    @Override
    public CategoryService getCrudService() {
        return categoryService;
    }

    @Override
    public CategorySearchDto getSearchDto() {
        return new CategorySearchDto();
    }

}