package org.arch.ums.feign.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code TagService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:35
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-tag", path = "/ums/account/tag",
        configuration = DeFaultFeignConfig.class)
public interface UmsAccountTagFeignService extends BaseFeignService<TagSearchDto, TagRequest, java.lang.Long> {

}
