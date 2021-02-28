package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.RoleGroupSearchDto;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.account.service.RoleGroupService;
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
 * 账号-角色组织或机构(RoleGroup) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:16:08
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/role/group")
public class RoleGroupController implements CrudController<RoleGroup, java.lang.Long, RoleGroupSearchDto, RoleGroupService> {

    private final TenantContextHolder tenantContextHolder;
    private final RoleGroupService roleGroupService;

    @Override
    public RoleGroup resolver(TokenInfo token, RoleGroup roleGroup) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            roleGroup.setTenantId(token.getTenantId());
        }
        else {
            roleGroup.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
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
