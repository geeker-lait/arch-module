package org.arch.ums.feign.account.client;

import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.vo.AuthClientVo;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * arch-ums-api {@code AuthClientService}服务远程调用的 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-oauthClient", path = "/ums/account/auth/client",
        configuration = UmsDeFaultFeignConfig.class)
public interface UmsAccountAuthClientFeignService extends BaseFeignService<AuthClient, Long> {

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @NonNull
    @PostMapping(value = "/scopes", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Response<Set<String>> getScopesByClientIdAndClientSecret(@RequestParam("clientId") String clientId,
                                                             @RequestParam("clientSecret") String clientSecret);

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes, Map(tenantId, Map(clientId, AuthClientVo))
     */
    @NonNull
    @GetMapping("/scopes/list")
    Response<Map<Integer, Map<String, AuthClientVo>>> getAllScopes();
}
