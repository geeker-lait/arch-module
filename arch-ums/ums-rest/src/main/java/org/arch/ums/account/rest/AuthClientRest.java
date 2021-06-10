package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.AuthClientRequest;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * 授权客户端(AuthClient) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:50:51
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/auth/client")
public interface AuthClientRest extends CrudRest<AuthClientRequest, java.lang.Long, AuthClientSearchDto> {

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     *
     * @param clientId     client id
     * @param clientSecret client secret
     * @return 返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @NonNull
    @PostMapping("/scopes")
    Set<String> getScopesByClientIdAndClientSecret(@RequestParam("clientId") String clientId,
                                                   @RequestParam("clientSecret") String clientSecret);

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes, Map(tenantId, Map(clientId, AuthClientVo))
     */
    @NonNull
    @GetMapping("/scopes/list")
    Map<Integer, Map<String, AuthClientVo>> getAllScopes();


}

