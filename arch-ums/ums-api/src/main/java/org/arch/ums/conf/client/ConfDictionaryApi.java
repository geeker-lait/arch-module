package org.arch.ums.conf.client;

import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code DictionaryService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:40:37
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-dictionary", path = "/ums/conf/dictionary",
        configuration = DeFaultFeignConfig.class)
public interface ConfDictionaryApi extends FeignApi<DictionarySearchDto, DictionaryRequest, Long> {

}
