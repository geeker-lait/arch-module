package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.service.ResourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-资源(Resource) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:40:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/resource")
public class ResourceController implements CrudController<Resource, Long, ResourceSearchDto, ResourceService> {

    private final TenantContextHolder tenantContextHolder;
    private final ResourceService resourceService;

    @Override
    public Resource resolver(TokenInfo token, Resource resource) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 resource 后返回 resource, 如: tenantId 的处理等.
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

}