package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.FeignCrudController;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.feign.account.client.UmsAccountResourceFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor
public class ResourceController implements FeignCrudController<Resource, Long, ResourceRequest, UmsAccountResourceFeignService> {

    private final UmsAccountResourceFeignService roleFeignService;

    @Override
    public UmsAccountResourceFeignService getFeignService() {
        return this.roleFeignService;
    }

    @Override
    public Resource getEntity() {
        return new Resource();
    }

}
