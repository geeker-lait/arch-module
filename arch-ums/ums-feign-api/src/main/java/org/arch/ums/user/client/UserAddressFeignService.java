package org.arch.ums.user.client;


import org.arch.framework.web.feign.BaseFeignService;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.user.dto.AddressRequest;
import org.arch.ums.user.dto.AddressSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code AddressService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:11:45
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-address", path = "/ums/user/address",
        configuration = DeFaultFeignConfig.class)
public interface UserAddressFeignService extends BaseFeignService<AddressSearchDto, AddressRequest, java.lang.Long> {

}
