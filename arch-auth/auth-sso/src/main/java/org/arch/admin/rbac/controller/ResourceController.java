package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.client.AccountResourceFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/rbac/resource")
@Slf4j
@RequiredArgsConstructor
public class ResourceController implements FeignCrudController<ResourceSearchDto, Long, ResourceRequest, AccountResourceFeignService> {

    private final AccountResourceFeignService roleFeignService;

    @Override
    public AccountResourceFeignService getFeignService() {
        return this.roleFeignService;
    }

}
