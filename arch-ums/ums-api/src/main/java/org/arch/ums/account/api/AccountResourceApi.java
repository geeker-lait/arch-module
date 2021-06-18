package org.arch.ums.account.api;

import org.arch.framework.beans.Response;
import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * arch-ums-api {@code ResourceService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:31
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-resource", path = "/ums/account/resource",
        configuration = DeFaultFeignConfig.class)
public interface AccountResourceApi extends FeignApi<ResourceSearchDto, ResourceRequest, Long> {

    /**
     * 多租户根据 {@code resourceIds} 获取 {@link ResourceSearchDto} 列表.
     *
     * @param tenantId    多租户 ID
     * @param resourceIds 资源 ID 列表
     * @return 资源列表, 只包含 {@code id, resourceCode, resourcePath, resourceVal} 字段
     */
    @GetMapping("/findByResourceIds/{tenantId}")
    @NonNull
    Response<List<ResourceSearchDto>> findByResourceIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                        @RequestBody List<Long> resourceIds);
}