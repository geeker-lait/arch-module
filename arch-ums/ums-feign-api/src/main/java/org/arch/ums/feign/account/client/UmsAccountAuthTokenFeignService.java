package org.arch.ums.feign.account.client;

import org.arch.framework.beans.Response;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.feign.account.config.UmsAccountDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * arch-ums-api {@code AuthTokenService}服务远程调用的 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-oauthToken", path = "/ums/account/oauth/token",
        configuration = UmsAccountDeFaultFeignConfig.class)
public interface UmsAccountAuthTokenFeignService {

    /**
     * 保存 oauthToken
     * @param oauthToken     实体类
     * @return  {@link Response}
     */
    @NonNull
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<OauthToken> save(@RequestBody @Valid OauthToken oauthToken);

    /**
     * 根据 identifier 更新 oauthToken
     * @param oauthToken     实体类
     * @return  {@link Response}
     */
    @NonNull
    @PostMapping(value = "/update/by/identifier", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<OauthToken> updateByIdentifier(@RequestBody @Valid OauthToken oauthToken);
}
