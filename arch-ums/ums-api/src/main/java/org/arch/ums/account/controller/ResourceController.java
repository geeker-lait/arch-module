package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.ResourceSearchDto;
import org.arch.ums.account.entity.Resource;
import org.arch.ums.account.service.ResourceService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-资源(Resource) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:16:07
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/resource")
public class ResourceController implements CrudController<Resource, java.lang.Long, ResourceSearchDto, ResourceService> {

    private final TenantContextHolder tenantContextHolder;
    private final ResourceService resourceService;

    @Override
    public Resource resolver(TokenInfo token, Resource resource) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            resource.setTenantId(token.getTenantId());
        }
        else {
            resource.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return resource;
    }

    @Override
    public ResourceService getCrudService() {
        return resourceService;
    }

    @Override
    public ResourceSearchDto getSearchDto() {
        return new ResourceSearchDto();
    }

}
