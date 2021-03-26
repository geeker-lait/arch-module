package org.arch.ums.feign.user.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.user.entity.IdCard;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code IdCardService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:11:44
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-idCard", path = "/ums/user/id/card",
        configuration = DeFaultFeignConfig.class)
public interface UmsUserIdCardFeignService extends BaseFeignService<IdCard, java.lang.Long> {

}
