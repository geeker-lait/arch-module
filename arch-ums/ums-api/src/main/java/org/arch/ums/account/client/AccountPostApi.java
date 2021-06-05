package org.arch.ums.account.client;


import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.PostRequest;
import org.arch.ums.account.dto.PostSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code PostService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:30
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-post", path = "/ums/account/post",
        configuration = DeFaultFeignConfig.class)
public interface AccountPostApi extends FeignApi<PostSearchDto, PostRequest, Long> {

}
