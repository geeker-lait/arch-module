package org.arch.ums.account.client;

import org.arch.framework.beans.Response;
import org.arch.framework.web.feign.BaseFeignService;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.OauthTokenRequest;
import org.arch.ums.account.dto.OauthTokenSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * arch-ums-api {@code AuthTokenService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-oauthToken", path = "/ums/account/oauth/token",
        configuration = DeFaultFeignConfig.class)
public interface AccountOauthTokenFeignService extends BaseFeignService<OauthTokenSearchDto, OauthTokenRequest, Long> {

    /**
     * 根据 identifierId 更新 oauthToken
     *
     * @param oauthToken 实体类
     * @return {@link Response}
     */
    @NonNull
    @PostMapping(value = "/updateByIdentifierId", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<Boolean> updateByIdentifierId(@RequestBody @Valid OauthTokenRequest oauthToken);
}
