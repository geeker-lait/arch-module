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

/**
 * 账号-资源(Resource) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:22:53
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/resource")
public class ResourceController implements CrudController<Resource, java.lang.Long, ResourceSearchDto, ResourceService> {

    private final ResourceService resourceService;

    @Override
    public Resource resolver(TokenInfo token, Resource resource) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 resource 后返回 resource, 如: tenantId 的处理等.
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