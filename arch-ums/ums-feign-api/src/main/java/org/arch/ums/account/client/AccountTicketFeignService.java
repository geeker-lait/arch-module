package org.arch.ums.account.client;


import org.arch.framework.web.feign.BaseFeignService;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.TicketRequest;
import org.arch.ums.account.dto.TicketSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code TicketService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:35
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-ticket", path = "/ums/account/ticket",
        configuration = DeFaultFeignConfig.class)
public interface AccountTicketFeignService extends BaseFeignService<TicketSearchDto, TicketRequest, Long> {

}
