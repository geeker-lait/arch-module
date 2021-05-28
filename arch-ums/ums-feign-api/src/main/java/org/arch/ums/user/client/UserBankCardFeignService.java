package org.arch.ums.user.client;


import org.arch.framework.web.feign.BaseFeignService;
import org.arch.ums.user.dto.BankCardRequest;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code BankCardService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:11:45
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-bankCard", path = "/ums/user/bank/card",
        configuration = DeFaultFeignConfig.class)
public interface UserBankCardFeignService extends BaseFeignService<BankCardSearchDto, BankCardRequest, Long> {

}
