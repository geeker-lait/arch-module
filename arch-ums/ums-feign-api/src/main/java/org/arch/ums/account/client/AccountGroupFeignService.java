package org.arch.ums.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.GroupRequest;
import org.arch.ums.account.dto.GroupSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code GroupService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:02:12
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-group", path = "/ums/account/group",
        configuration = DeFaultFeignConfig.class)
public interface AccountGroupFeignService extends BaseFeignService<GroupSearchDto, GroupRequest, java.lang.Long> {

}
