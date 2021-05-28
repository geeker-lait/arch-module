package org.arch.ums.user.client;


import org.arch.framework.web.feign.BaseFeignService;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.user.dto.IdCardRequest;
import org.arch.ums.user.dto.IdCardSearchDto;
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
public interface UserIdCardFeignService extends BaseFeignService<IdCardSearchDto, IdCardRequest, Long> {

}
