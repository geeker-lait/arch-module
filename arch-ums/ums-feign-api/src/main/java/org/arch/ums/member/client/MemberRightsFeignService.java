package org.arch.ums.member.client;


import org.arch.framework.web.feign.BaseFeignService;
import org.arch.ums.member.dto.MemberRightsSearchDto;
import org.arch.ums.member.dto.MemberRightsRequest;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code MemberRightsService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-05-19 16:32:28
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-memberRights", path = "/ums/member/rights",
        configuration = DeFaultFeignConfig.class)
public interface MemberRightsFeignService extends BaseFeignService<MemberRightsSearchDto, MemberRightsRequest, java.lang.Long> {

}
