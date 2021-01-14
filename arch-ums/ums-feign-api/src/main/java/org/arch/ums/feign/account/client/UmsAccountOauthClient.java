package org.arch.ums.feign.account.client;

import org.arch.ums.feign.account.config.UmsAccountDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * arch-ums-api 服务远程调用的 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(value = "arch-ums-api", path = "/ums/account/oauthClient", configuration = UmsAccountDeFaultFeignConfig.class)
public interface UmsAccountOauthClient {

    /**
     * 根据 appId 与 appCode 查询 scopes
     * @param appId    app id
     * @param appCode  app secret
     * @return  返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @GetMapping("/scopes")
    Set<String> getScopesByAppIdAndAppCode(@RequestParam("appId") String appId,
                                           @RequestParam("appCode") String appCode);

}