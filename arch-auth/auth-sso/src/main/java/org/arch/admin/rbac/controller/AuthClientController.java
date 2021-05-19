package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.account.dto.AuthClientRequest;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.client.AccountAuthClientFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SCOPE CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/rbac/auth/client")
@Slf4j
@RequiredArgsConstructor
public class AuthClientController implements FeignCrudController<AuthClientSearchDto, Long, AuthClientRequest,
        AccountAuthClientFeignService> {

    private final AccountAuthClientFeignService authClientFeignService;

    @Override
    public AccountAuthClientFeignService getFeignService() {
        return this.authClientFeignService;
    }

}
