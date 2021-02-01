package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.account.dto.RoleGroupSearchDto;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.account.service.RoleGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 账号-角色组织或机构(RoleGroup) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:40:36
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/group")
public class RoleGroupController implements CrudController<RoleGroup, Long, RoleGroupSearchDto, RoleGroupService> {

    private final AppProperties appProperties;
    private final RoleGroupService roleGroupService;

    @Override
    public RoleGroup resolver(TokenInfo token, RoleGroup roleGroup) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 roleGroup 后返回 roleGroup, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            roleGroup.setTenantId(token.getTenantId());
        }
        else {
            roleGroup.setTenantId(appProperties.getSystemTenantId());
        }
        return roleGroup;
    }

    @Override
    public RoleGroupService getCrudService() {
        return roleGroupService;
    }

    @Override
    public RoleGroupSearchDto getSearchDto() {
        return new RoleGroupSearchDto();
    }

}