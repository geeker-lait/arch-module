package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountAuthClientApi;
import org.arch.ums.account.dto.AuthClientRequest;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SCOPE CRUD 控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController("adminAuthClientController")
@RequestMapping("/rbac/auth/client")
@Slf4j
@RequiredArgsConstructor
public class AuthClientApi implements FeignCrudApi<AuthClientSearchDto, Long, AuthClientRequest,
        AccountAuthClientApi> {

    private final AccountAuthClientApi accountAuthClientApi;

    @Override
    public AccountAuthClientApi getApi() {
        return this.accountAuthClientApi;
    }

}
