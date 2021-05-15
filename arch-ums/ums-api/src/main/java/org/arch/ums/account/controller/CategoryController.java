package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.CategoryRequest;
import org.arch.ums.account.dto.CategorySearchDto;
import org.arch.ums.account.entity.Category;
import org.arch.ums.account.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-资源类目(Category) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 21:39:19
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/category")
public class CategoryController implements CrudController<CategoryRequest, Category, java.lang.Long, CategorySearchDto, CategoryService> {

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
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<CategorySearchDto> findOne(@RequestBody @Valid CategoryRequest request, TokenInfo token) {
        try {
            Category category = resolver(token, request);
            CategorySearchDto searchDto = convertSearchDto(category);
            Category result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(convertSearchDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<CategorySearchDto>> find(@RequestBody @Valid CategoryRequest request, TokenInfo token) {
        Category category = resolver(token, request);
        CategorySearchDto searchDto = convertSearchDto(category);
        try {
            List<Category> categoryList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(categoryList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<CategorySearchDto>> page(@RequestBody @Valid CategoryRequest request,
                                                   @PathVariable(value = "pageNumber") Integer pageNumber,
                                                   @PathVariable(value = "pageSize") Integer pageSize,
                                                   TokenInfo token) {
        Category category = resolver(token, request);
        CategorySearchDto searchDto = convertSearchDto(category);
        try {
            IPage<Category> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
