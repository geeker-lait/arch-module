package org.arch.ums.feign.conf.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.ums.feign.config.UmsDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-conf-api {@code ConfMobileInfoService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:51:27
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-conf-api", contextId = "arch-conf-api-confMobileInfo", path = "/ums/conf/mobile/info",
        configuration = UmsDeFaultFeignConfig.class)
public interface ConfMobileInfoFeignService extends BaseFeignService<MobileInfo, java.lang.Long> {

}
