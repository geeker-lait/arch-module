package org.arch.ums.feign.account.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.entity.Relationship;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code RelationshipService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-13 12:33:38
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-relationship", path = "/ums/account/relationship",
        configuration = DeFaultFeignConfig.class)
public interface UmsAccountRelationshipFeignService extends BaseFeignService<Relationship, java.lang.Long> {

}