package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.CategoryRequest;
import org.arch.ums.account.dto.CategorySearchDto;
import org.arch.ums.account.entity.Category;
import org.arch.ums.account.rest.CategoryRest;
import org.arch.ums.account.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-资源类目(Category) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:01
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryBiz implements CrudBiz<CategoryRequest, Category, java.lang.Long, CategorySearchDto, CategorySearchDto, CategoryService>, CategoryRest {

    private final TenantContextHolder tenantContextHolder;
    private final CategoryService categoryService;

    @Override
    public Category resolver(TokenInfo token, CategoryRequest request) {
        Category category = new Category();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, category);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            category.setTenantId(token.getTenantId());
        }
        else {
            category.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
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
