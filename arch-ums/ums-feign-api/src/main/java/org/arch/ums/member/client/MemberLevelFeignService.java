package org.arch.ums.member.client;


import org.arch.framework.web.feign.BaseFeignService;
import org.arch.ums.member.dto.MemberLevelSearchDto;
import org.arch.ums.member.dto.MemberLevelRequest;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code MemberLevelService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-05-19 16:32:28
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-memberLevel", path = "/ums/member/level",
        configuration = DeFaultFeignConfig.class)
public interface MemberLevelFeignService extends BaseFeignService<MemberLevelSearchDto, MemberLevelRequest, java.lang.Long> {

}
