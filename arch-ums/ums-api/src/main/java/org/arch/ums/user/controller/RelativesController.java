package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.RelativesSearchDto;
import org.arch.ums.user.entity.Relatives;
import org.arch.ums.user.service.RelativesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户亲朋信息(Relatives) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:31:22
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/relatives")
public class RelativesController implements CrudController<Relatives, Long, RelativesSearchDto, RelativesService> {

    private final TenantContextHolder tenantContextHolder;
    private final RelativesService relativesService;

    @Override
    public Relatives resolver(TokenInfo token, Relatives relatives) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 relatives 后返回 relatives, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            relatives.setTenantId(token.getTenantId());
        }
        else {
            relatives.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return relatives;
    }

    @Override
    public RelativesService getCrudService() {
        return relativesService;
    }

    @Override
    public RelativesSearchDto getSearchDto() {
        return new RelativesSearchDto();
    }

}