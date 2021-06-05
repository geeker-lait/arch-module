package org.arch.ums.account.client;


import org.arch.framework.beans.Response;
import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.account.dto.PermissionRequest;
import org.arch.ums.account.dto.PermissionSearchDto;
import org.arch.ums.account.entity.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * arch-ums-api {@code PermissionService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:04:30
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-permission", path = "/ums/account/permission",
        configuration = DeFaultFeignConfig.class)
public interface AccountPermissionApi extends FeignApi<PermissionSearchDto, PermissionRequest, Long> {

    /**
     * 多租户根据 {@code permissionIds} 获取 {@link Permission} 列表.
     *
     * @param tenantId      多租户 ID
     * @param permissionIds 权限 ID 列表
     * @return 权限列表, 只包含 {@code id, permissionCode, permissionUri, permissionVal} 字段
     */
    @GetMapping("/findByPermissionIds/{tenantId}")
    @NonNull
    Response<List<PermissionSearchDto>> findByPermissionIds(@PathVariable(value = "tenantId") Integer tenantId,
                                                            @RequestBody List<Long> permissionIds);
}
