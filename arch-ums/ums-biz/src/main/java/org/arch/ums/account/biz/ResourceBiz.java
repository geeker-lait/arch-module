package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.rest.ResourceRest;
import org.arch.ums.account.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-资源(Resource) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ResourceBiz implements CrudBiz<ResourceRequest, Resource, java.lang.Long, ResourceSearchDto, ResourceSearchDto, ResourceService>, ResourceRest {

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public ResourceSearchDto findOne(ResourceRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Resource resource = resolver(token, request);
        ResourceSearchDto searchDto = convertSearchDto(resource);
        Resource result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<ResourceSearchDto> find(ResourceRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Resource resource = resolver(token, request);
        ResourceSearchDto searchDto = convertSearchDto(resource);
        List<Resource> resourceList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return resourceList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<ResourceSearchDto> page(ResourceRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Resource resource = resolver(token, request);
        ResourceSearchDto searchDto = convertSearchDto(resource);
        IPage<Resource> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 多租户根据 {@code resourceIds} 获取 {@link Resource} 列表.
     *
     * @param tenantId    多租户 ID
     * @param resourceIds 资源 ID 列表
     * @return 资源列表, 只包含 {@code id, resourceCode, resourcePath, resourceVal} 字段
     */
    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ResourceSearchDto> findByResourceIds(Integer tenantId, List<Long> resourceIds) {
        Wrapper<Resource> resourceWrapper = Wrappers.lambdaQuery(Resource.class)
                                                    .select(Resource::getId,
                                                            Resource::getResourceCode,
                                                            Resource::getResourcePath,
                                                            Resource::getResourceVal)
                                                    .eq(Resource::getTenantId, tenantId)
                                                    .in(Resource::getId, resourceIds)
                                                    .eq(Resource::getDeleted, Boolean.FALSE);
        List<Resource> resourceList = resourceService.findAllBySpec(resourceWrapper);
        return resourceList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

}
