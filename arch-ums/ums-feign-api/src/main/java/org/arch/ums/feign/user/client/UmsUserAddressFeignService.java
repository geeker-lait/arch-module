package org.arch.ums.feign.user.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.user.entity.Address;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
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
        configuration = UmsDeFaultFeignConfig.class)
public interface UmsUserAddressFeignService extends BaseFeignService<Address, java.lang.Long> {

}
