package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.FeignCrudController;
import org.arch.ums.account.dto.AuthClientRequest;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.feign.account.client.UmsAccountAuthClientFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SCOPE CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor
public class AuthClientController implements FeignCrudController<AuthClient, Long, AuthClientRequest,
        UmsAccountAuthClientFeignService> {

    private final UmsAccountAuthClientFeignService authClientFeignService;

    @Override
    public UmsAccountAuthClientFeignService getFeignService() {
        return this.authClientFeignService;
    }

    @Override
    public AuthClient getEntity() {
        return new AuthClient();
    }

}
