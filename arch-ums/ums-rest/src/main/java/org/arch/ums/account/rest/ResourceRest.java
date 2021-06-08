package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.ResourceRequest;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号-资源(Resource) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:56
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/resource")
public interface ResourceRest extends CrudRest<ResourceRequest, java.lang.Long, ResourceSearchDto> {
    /**
     * 多租户根据 {@code resourceIds} 获取 {@link Resource} 列表.
     *
     * @param tenantId    多租户 ID
     * @param resourceIds 资源 ID 列表
     * @return 资源列表, 只包含 {@code id, resourceCode, resourcePath, resourceVal} 字段
     */
    @GetMapping("/findByResourceIds/{tenantId}")
    @NonNull
    List<ResourceSearchDto> findByResourceIds(@PathVariable(value = "tenantId") Integer tenantId,
                                              @RequestBody List<Long> resourceIds);
}

