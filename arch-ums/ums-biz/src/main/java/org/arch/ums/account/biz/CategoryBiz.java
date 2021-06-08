package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.CategoryRequest;
import org.arch.ums.account.dto.CategorySearchDto;
import org.arch.ums.account.entity.Category;
import org.arch.ums.account.rest.CategoryRest;
import org.arch.ums.account.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public CategorySearchDto findOne(CategoryRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Category category = resolver(token, request);
        CategorySearchDto searchDto = convertSearchDto(category);
        Category result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<CategorySearchDto> find(CategoryRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Category category = resolver(token, request);
        CategorySearchDto searchDto = convertSearchDto(category);
        List<Category> categoryList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return categoryList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<CategorySearchDto> page(CategoryRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Category category = resolver(token, request);
        CategorySearchDto searchDto = convertSearchDto(category);
        IPage<Category> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
