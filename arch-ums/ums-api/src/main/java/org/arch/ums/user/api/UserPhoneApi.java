package org.arch.ums.user.api;


import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.user.dto.PhoneRequest;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code PhoneService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:11:44
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-phone", path = "/ums/user/phone",
        configuration = DeFaultFeignConfig.class)
public interface UserPhoneApi extends FeignApi<PhoneSearchDto, PhoneRequest, Long> {

}
