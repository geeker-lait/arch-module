package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.service.AuthClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 授权客户端(AuthClient) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/auth/client")
public class AuthClientController implements CrudController<AuthClient, java.lang.Long, AuthClientSearchDto, AuthClientService> {

    private final AuthClientService authClientService;

    @Override
    public AuthClient resolver(TokenInfo token, AuthClient authClient) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 authClient 后返回 authClient, 如: tenantId 的处理等.
        return authClient;
    }

    @Override
    public AuthClientService getCrudService() {
        return authClientService;
    }

    @Override
    public AuthClientSearchDto getSearchDto() {
        return new AuthClientSearchDto();
    }

//    @PostMapping("/scopes")
//    public Set<String> getScopesByAppIdAndAppCode(@RequestParam("appId") String appId,
//                                                  @RequestParam("appCode") String appCode) {
//        return accountOauthClientService.getScopesByAppIdAndAppCode(appId, appCode);
//    }

}