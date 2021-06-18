package org.arch.auth.rbac.feign;


import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * arch-ums-api {@code ResourceService}服务远程调用的 feign 客户端.
 * 注意: 与 ums-feign-api 模块的 {@code UmsAccountResourceFeignService} 逻辑相同, 添加接口时需一同修改.
 * @author YongWu zheng
 * @date 2021-03-06 15:04:31
 * @since 1.0.0
 */
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-rbac-resource", path = "/ums/account/resource",
        configuration = DeFaultFeignConfig.class)
public interface ResourceFeignService extends BaseFeignService<ResourceSearchDto, ResourceRequest, Long> {

    /**
     * 多租户根据 {@code resourceIds} 获取 {@link Resource} 列表.
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
