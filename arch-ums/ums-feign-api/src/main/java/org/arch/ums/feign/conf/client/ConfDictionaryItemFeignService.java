package org.arch.ums.feign.conf.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.arch.ums.conf.entity.DictionaryItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code DictionaryItemService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:40:37
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-dictionaryItem", path = "/ums/conf/dictionary/item",
        configuration = DeFaultFeignConfig.class)
public interface ConfDictionaryItemFeignService extends BaseFeignService<DictionaryItemSearchDto, DictionaryItemRequest, Long> {

}
