package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.service.AuthClientService;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

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
    private final AppProperties appProperties;

    @Override
    public AuthClient resolver(TokenInfo token, AuthClient authClient) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            authClient.setTenantId(token.getTenantId());
        }
        else {
            authClient.setTenantId(appProperties.getSystemTenantId());
        }
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

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @PostMapping("/scopes")
    public Response<Set<String>> getScopesByClientIdAndClientSecret(@RequestParam("clientId") String clientId,
                                                                   @RequestParam("clientSecret") String clientSecret) {
        return Response.success(authClientService.getScopesByClientIdAndClientSecret(clientId, clientSecret));
    }

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes
     */
    @GetMapping("/scopes/list")
    public Response<Map<Integer, Map<String, AuthClientVo>>> getAllScopes() {
        return Response.success(authClientService.getAllScopes());
    }



}