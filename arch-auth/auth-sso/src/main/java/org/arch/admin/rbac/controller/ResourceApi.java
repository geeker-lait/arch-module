package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountResourceApi;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源 CRUD 控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController("adminResourceController")
@RequestMapping("/rbac/resource")
@Slf4j
@RequiredArgsConstructor
public class ResourceApi implements FeignCrudApi<ResourceSearchDto, Long, ResourceRequest, AccountResourceApi> {

    private final AccountResourceApi accountResourceApi;

    @Override
    public AccountResourceApi getApi() {
        return this.accountResourceApi;
    }

}
