package org.arch.ums.feign.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.Post;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
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
        configuration = UmsDeFaultFeignConfig.class)
public interface UmsAccountPostFeignService extends BaseFeignService<Post, java.lang.Long> {

}
