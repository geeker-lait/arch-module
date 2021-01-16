package org.arch.ums.feign.account.client;

import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.entity.AccountIdentifier;
import org.arch.ums.feign.account.config.UmsAccountDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户登录与注册服务的 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-identifier", path = "/ums/account/identifier",
        configuration = UmsAccountDeFaultFeignConfig.class)
public interface UmsAccountClient {

    /**
     * 通过 {@link AccountIdentifier#getIdentifier()} 来获取 {@link AccountIdentifier}
     * @param identifier    用户唯一标识
     * @return  返回 {@link AuthAccountDto}
     */
    @GetMapping("/loadAccount/{identifier}")
    AuthAccountDto loadAccountByIdentifier(@PathVariable("identifier") String identifier);

}
