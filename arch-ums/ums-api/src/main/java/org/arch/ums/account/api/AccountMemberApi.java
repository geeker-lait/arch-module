package org.arch.ums.account.api;

import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.MemberRequest;
import org.arch.ums.account.dto.MemberSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code MemberService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-13 12:33:32
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-member", path = "/ums/account/member",
        configuration = DeFaultFeignConfig.class)
public interface AccountMemberApi extends FeignApi<MemberSearchDto, MemberRequest, Long> {

}