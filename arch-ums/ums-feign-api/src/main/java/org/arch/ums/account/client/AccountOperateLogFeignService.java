package org.arch.ums.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.dto.OperateLogRequest;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.arch.ums.account.entity.OperateLog;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code OperateLogService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:29
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-operateLog", path = "/ums/account/operate/log",
        configuration = DeFaultFeignConfig.class)
public interface AccountOperateLogFeignService extends BaseFeignService<OperateLogSearchDto, OperateLogRequest, Long> {

}
