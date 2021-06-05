package org.arch.ums.user.client;


import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code EducationService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:11:43
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-education", path = "/ums/user/education",
        configuration = DeFaultFeignConfig.class)
public interface UserEducationApi extends FeignApi<EducationSearchDto, EducationRequest, Long> {

}
