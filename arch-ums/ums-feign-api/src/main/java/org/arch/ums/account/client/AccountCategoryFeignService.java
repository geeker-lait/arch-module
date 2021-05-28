package org.arch.ums.account.client;

import org.arch.framework.web.feign.BaseFeignService;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.CategoryRequest;
import org.arch.ums.account.dto.CategorySearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code CategoryService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:00:33
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-category", path = "/ums/account/category",
        configuration = DeFaultFeignConfig.class)
public interface AccountCategoryFeignService extends BaseFeignService<CategorySearchDto, CategoryRequest, Long> {

}
