package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RelationshipSearchDto;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.service.RelationshipService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-关系(Relationship) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:39:48
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/relationship")
public class RelationshipController implements CrudController<Relationship, Long, RelationshipSearchDto, RelationshipService> {

    private final TenantContextHolder tenantContextHolder;
    private final RelationshipService relationshipService;

    @Override
    public Relationship resolver(TokenInfo token, Relationship relationship) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 relationship 后返回 relationship, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            relationship.setTenantId(token.getTenantId());
        }
        else {
            relationship.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return relationship;
    }

    @Override
    public RelationshipService getCrudService() {
        return relationshipService;
    }

    @Override
    public RelationshipSearchDto getSearchDto() {
        return new RelationshipSearchDto();
    }

}