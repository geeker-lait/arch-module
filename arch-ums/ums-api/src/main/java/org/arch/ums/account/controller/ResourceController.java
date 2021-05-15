package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.service.ResourceService;
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
 * 账号-资源(Resource) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:12:34
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/resource")
public class ResourceController implements CrudController<ResourceRequest, Resource, java.lang.Long, ResourceSearchDto, ResourceService> {

    private final TenantContextHolder tenantContextHolder;
    private final ResourceService resourceService;

    @Override
    public Resource resolver(TokenInfo token, ResourceRequest request) {
        Resource resource = new Resource();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, resource);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            resource.setTenantId(token.getTenantId());
        }
        else {
            resource.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return resource;
    }

    @Override
    public ResourceService getCrudService() {
        return resourceService;
    }

    @Override
    public ResourceSearchDto getSearchDto() {
        return new ResourceSearchDto();
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
    public Response<ResourceSearchDto> findOne(@RequestBody @Valid ResourceRequest request, TokenInfo token) {
        try {
            Resource resource = resolver(token, request);
            ResourceSearchDto searchDto = convertSearchDto(resource);
            Resource result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<ResourceSearchDto>> find(@RequestBody @Valid ResourceRequest request, TokenInfo token) {
        Resource resource = resolver(token, request);
        ResourceSearchDto searchDto = convertSearchDto(resource);
        try {
            List<Resource> resourceList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(resourceList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<ResourceSearchDto>> page(@RequestBody @Valid ResourceRequest request,
                                                   @PathVariable(value = "pageNumber") Integer pageNumber,
                                                   @PathVariable(value = "pageSize") Integer pageSize,
                                                   TokenInfo token) {
        Resource resource = resolver(token, request);
        ResourceSearchDto searchDto = convertSearchDto(resource);
        try {
            IPage<Resource> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 多租户根据 {@code resourceIds} 获取 {@link Resource} 列表.
     *
     * @param tenantId    多租户 ID
     * @param resourceIds 资源 ID 列表
     * @return 资源列表, 只包含 {@code id, resourceCode, resourcePath, resourceVal} 字段
     */
    @GetMapping("/findByResourceIds/{tenantId}")
    @NonNull
    public Response<List<ResourceSearchDto>> findByResourceIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                               @RequestBody List<Long> resourceIds) {
        Wrapper<Resource> resourceWrapper = Wrappers.lambdaQuery(Resource.class)
                                                    .select(Resource::getId,
                                                            Resource::getResourceCode,
                                                            Resource::getResourcePath,
                                                            Resource::getResourceVal)
                                                    .eq(Resource::getTenantId, tenantId)
                                                    .in(Resource::getId, resourceIds)
                                                    .eq(Resource::getDeleted, Boolean.FALSE);
        try {
            List<Resource> resourceList = resourceService.findAllBySpec(resourceWrapper);
            return Response.success(resourceList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
